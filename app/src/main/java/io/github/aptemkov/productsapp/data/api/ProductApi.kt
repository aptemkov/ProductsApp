package io.github.aptemkov.productsapp.data.api

import io.github.aptemkov.productsapp.domain.models.ProductResponseDomain
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products/")
    fun getProductsList(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20,
    ): Single<ProductResponseDomain>
}