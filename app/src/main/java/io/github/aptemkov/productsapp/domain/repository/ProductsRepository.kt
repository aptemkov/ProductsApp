package io.github.aptemkov.productsapp.domain.repository

import io.github.aptemkov.productsapp.domain.models.ProductResponseDomain
import io.reactivex.rxjava3.core.Single

interface ProductsRepository {

    fun getAllProducts(
        skip: Int = 0,
        limit: Int = 20,
    ): Single<ProductResponseDomain>

}