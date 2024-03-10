package io.github.aptemkov.productsapp.app.presentation

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.aptemkov.productsapp.R
import io.github.aptemkov.productsapp.app.presentation.adapter.ProductsAdapter
import io.github.aptemkov.productsapp.databinding.ActivityMainBinding
import io.github.aptemkov.productsapp.domain.models.ProductDomain
import io.github.aptemkov.productsapp.utils.MAIN_ACTIVITY_TAG
import io.github.aptemkov.productsapp.utils.Response
import io.github.aptemkov.productsapp.utils.hasInternetConnection
import io.github.aptemkov.productsapp.utils.hideKeyboard
import io.github.aptemkov.productsapp.utils.log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupUI()
        setupDataSource()
        setupSearchView()
    }

    /**
     * Вероятно, не самая лучшая реализация поиска, но я постарался сделать его удобным для пользователя
     */
    private fun setupSearchView() {
        val disposable = Observable.create<String> { emitter ->
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(newText ?: " ")
                    }
                    return false
                }

            })
        }
            .debounce(700, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { query ->
                viewModel.searchByQuery(query)
            }
    }

    private fun setupDataSource() {
        viewModel.getProductsList().observe(this) {
            when (it) {
                is Response.Success -> onSuccess(it.data)

                is Response.Error -> onError(it.message)

                is Response.Loading -> onLoading()
            }
        }
    }

    private fun onSuccess(data: List<ProductDomain>?) {
        disableProgressIndicator()
        closeException()
        adapter.data = checkNotNull(data)

        log(MAIN_ACTIVITY_TAG, "Success: ${data.size}")
    }

    private fun onError(message: String?) {
        showException(message ?: getString(R.string.exception))
        disableProgressIndicator()

        log(MAIN_ACTIVITY_TAG, "Exception: $message")
    }

    private fun onLoading() {
        enableProgressIndicator()

        log(MAIN_ACTIVITY_TAG, "Loading")
    }

    private fun setupUI() {
        val layoutManager = LinearLayoutManager(this)
        adapter = ProductsAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun enableProgressIndicator() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun disableProgressIndicator() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showException(message: String) {

        binding.exceptionText.text =
            if (hasInternetConnection()) message
            else getString(R.string.you_have_no_internet_connection)
        binding.exceptionText.visibility = View.VISIBLE
        binding.exceptionRetryButton.visibility = View.VISIBLE


        binding.exceptionRetryButton.setOnClickListener {
            viewModel.fetchAllProducts()
            closeException()
            enableProgressIndicator()
        }
    }

    private fun closeException() {
        binding.exceptionText.visibility = View.INVISIBLE
        binding.exceptionRetryButton.visibility = View.INVISIBLE
    }

}
