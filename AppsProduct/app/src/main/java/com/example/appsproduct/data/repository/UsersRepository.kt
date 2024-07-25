package com.example.appsproduct.data.repository

import com.example.appsproduct.domain.model.Users
import com.example.appsproduct.data.result.Result
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUser(id: Int): Flow<Result<Users>>
    suspend fun addUser(users: Users)
    suspend fun resetUser(id: Int)
    suspend fun getUserFromDB(id: Int): Flow<Result<Users>>
}