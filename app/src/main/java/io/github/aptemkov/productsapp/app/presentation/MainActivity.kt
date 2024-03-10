package io.github.aptemkov.productsapp.app.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.aptemkov.productsapp.utils.Response
import io.github.aptemkov.productsapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.products.observe(this) {
            when(it) {
                is Response.Success -> {
                    Log.i("TAG123", "Success: ${it.data} ")
                }
                is Response.Error -> {
                    Log.i("TAG123", "Error: ${it.message} ")
                }
                is Response.Loading -> {
                    Log.i("TAG123", "Loading")
                }
            }
        }
    }
}
