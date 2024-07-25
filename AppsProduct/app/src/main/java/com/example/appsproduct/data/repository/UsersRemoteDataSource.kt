package com.example.appsproduct.data.repository

import com.example.appsproduct.data.remote.ApiService
import com.example.appsproduct.domain.model.Users
import retrofit2.Response

class UsersRemoteDataSource(private val apiService: ApiService) : UserRemoteDataSource {
    override suspend fun getUser(id: Int): Response<Users> {
        return apiService.getUsersId(id)
    }
}