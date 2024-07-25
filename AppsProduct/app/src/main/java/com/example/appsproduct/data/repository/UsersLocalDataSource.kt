package com.example.appsproduct.data.repository

import com.example.appsproduct.data.local.dao.UserDao
import com.example.appsproduct.domain.model.Users

class UsersLocalDataSource(private val userDao: UserDao) :UserLocalDataSource {

    override fun getUser(id: Int): Users {
        return userDao.getUser(id)
    }

    override suspend fun addUser(users: Users) {
        userDao.insertUser(users)
    }

    override suspend fun deleteUser(id: Int) {
        userDao.deleteUser(id)
    }
}