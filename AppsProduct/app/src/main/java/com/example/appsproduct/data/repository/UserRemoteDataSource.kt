package com.example.appsproduct.data.repository

import com.example.appsproduct.domain.model.Users
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getUser(id:Int): Response<Users>
}