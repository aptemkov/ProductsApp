package io.github.aptemkov.productsapp.app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aptemkov.productsapp.utils.Response
import io.github.aptemkov.productsapp.domain.models.ProductDomain
import io.github.aptemkov.productsapp.domain.repository.ProductsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductsRepository,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val productsMutable = MutableLiveData<Response<List<ProductDomain>>>()
    val products: LiveData<Response<List<ProductDomain>>>
        get() = productsMutable

    init {
        fetchAllProducts()
    }

    fun fetchAllProducts() {
        productsMutable.postValue(Response.Loading())

        compositeDisposable.add(
            repository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    //onSuccess
                    { productResponse ->
                        productsMutable.postValue(
                            Response.Success(productResponse.products)
                        )
                    },
                    //onError
                    {
                        productsMutable.postValue(
                            Response.Error(it.localizedMessage)
                        )
                    }
                )
        )
    }

    fun getProductsList() = products

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}