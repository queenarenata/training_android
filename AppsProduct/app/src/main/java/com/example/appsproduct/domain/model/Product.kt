package com.example.appsproduct.domain.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val thumbnail: String,
    val rating: Double,
    val review: String
)