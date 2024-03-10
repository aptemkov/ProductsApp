package io.github.aptemkov.productsapp.data.api

import io.github.aptemkov.productsapp.domain.models.ProductResponseDomain
import io.github.aptemkov.productsapp.domain.repository.ProductsRepository
import io.github.aptemkov.productsapp.utils.PER_PAGE
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products/")
    fun getProductsList(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = PER_PAGE,
    ): Single<ProductResponseDomain>

    @GET("products/search")
    fun searchProductsByQuery(
        @Query("q") query: String,
    ): Single<ProductResponseDomain>
}