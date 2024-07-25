package com.example.appsproduct.data.remote

import com.example.appsproduct.ui.fragment.DetailActivity
import com.example.appsproduct.domain.model.Users
import com.example.appsproduct.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int,
        @Query("select") select: String = "title,price,stock,thumbnail,description,rating,review"
    ): ProductResponse

    @GET("products/{id}")
    suspend fun getProductId(
        @Path("id") id: Int
    ): Response<DetailActivity>

    @GET("users/{id}")
    suspend fun getUsersId(
        @Path("id") id: Int
    ): Response<Users>

    @GET("favoriteProducts")
    suspend fun getFavoriteProducts(): List<Product>
}

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)