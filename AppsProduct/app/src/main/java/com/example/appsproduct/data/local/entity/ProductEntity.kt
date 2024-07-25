package com.example.appsproduct.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val stock: String,
    val image: String
)