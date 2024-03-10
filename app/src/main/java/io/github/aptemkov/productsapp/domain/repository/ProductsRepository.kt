package io.github.aptemkov.productsapp.domain.repository

import io.github.aptemkov.productsapp.domain.models.ProductResponseDomain
import io.github.aptemkov.productsapp.utils.PER_PAGE
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface ProductsRepository {

    fun getAllProducts(
        skip: Int = 0,
        limit: Int = PER_PAGE,
    ): Single<ProductResponseDomain>

    fun searchProductsByQuery(
        query: String,
    ): Single<ProductResponseDomain>

}