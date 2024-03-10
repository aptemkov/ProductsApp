package io.github.aptemkov.productsapp.domain.models

data class ProductDomain(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val brand: String,
    val category: String,
    val thumbnail: String,
)