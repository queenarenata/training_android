package com.example.appsproduct.data.repository

import com.example.appsproduct.domain.model.Users

interface UserLocalDataSource {
    fun getUser(id: Int): Users
    suspend fun addUser(users: Users)
    suspend fun deleteUser(id: Int)
}