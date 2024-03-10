package io.github.aptemkov.productsapp.data.repository

import io.github.aptemkov.productsapp.data.api.ProductApi
import io.github.aptemkov.productsapp.domain.models.ProductResponseDomain
import io.github.aptemkov.productsapp.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
) : ProductsRepository {
    override fun getAllProducts(skip: Int, limit: Int): Single<ProductResponseDomain> {
        return productApi.getProductsList()
    }

}
