package com.example.appsproduct.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsproduct.domain.model.Users

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: Users)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Int)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): Users
}