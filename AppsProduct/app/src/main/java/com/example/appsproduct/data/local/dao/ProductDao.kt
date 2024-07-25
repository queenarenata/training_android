package com.example.appsproduct.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.appsproduct.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<ProductEntity>>

    @Insert
    suspend fun insert(product: ProductEntity): Long

    @Delete
    suspend fun delete(product: ProductEntity): Int

    @Query("SELECT * FROM favorites")
    fun getListFavorites(): PagingSource<Int, ProductEntity>
}