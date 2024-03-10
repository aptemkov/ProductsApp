package io.github.aptemkov.productsapp.data.models

import io.github.aptemkov.productsapp.domain.models.ProductDomain

data class Product(
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

fun Product.toDomain(): ProductDomain {
    return ProductDomain(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        brand = this.brand,
        category = this.category,
        thumbnail = this.thumbnail,
    )
}