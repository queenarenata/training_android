package com.example.appsproduct.data.repository

import android.content.Context
import android.util.Log
import com.example.appsproduct.data.remote.ApiService
import com.example.appsproduct.domain.model.Users
import com.example.appsproduct.data.local.AppDatabase
import com.example.appsproduct.data.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val api: ApiService,
    private val context: Context
) : UsersRepository {

    private val dao = AppDatabase.getDatabase(context).userDao()
    private val remoteDataSource = UsersRemoteDataSource(api)
    private val localDataSource = UsersLocalDataSource(dao)

    override fun getUser(id: Int): Flow<Result<Users>> {
        return flow {
            emit(Result.Loading)
            try {
                emit(Result.Success(getData(id)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    }

    override suspend fun addUser(users: Users) {
        localDataSource.addUser(users)
    }

    override suspend fun resetUser(id: Int) {
        localDataSource.deleteUser(id)
    }

    override suspend fun getUserFromDB(id: Int): Flow<Result<Users>> {
        return flow {
            emit(Result.Loading)
            try {
                emit(Result.Success(localDataSource.getUser(id)))
            } catch (e: Exception) {
                Log.i("DBProducts", e.message.toString())
                emit(Result.Failure(e))
            }
        }
    }

    private suspend fun getData(id: Int): Users {
        var user: Users? = null
        try {
            user = localDataSource.getUser(id)
        } catch (e: Exception) {
            Log.i("Products", e.message.toString())
        }
        if (user != null) {
            return user
        } else {
            user = getDataFromAPI(id)
            localDataSource.addUser(user)
        }
        return user
    }

    private suspend fun getDataFromAPI(id: Int): Users {
        var users: Users? = null
        try {
            val response = remoteDataSource.getUser(id)
            users = response.body()
        } catch (e: Exception) {
            Log.i("Products", e.message.toString())
        }
        return users!!
    }
}