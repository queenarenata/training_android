package com.example.appsproduct.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.appsproduct.data.local.AppDatabase
import com.example.appsproduct.data.local.entity.ProductEntity
import com.example.appsproduct.data.remote.ApiService
import com.example.appsproduct.data.remote.paging.ProductPagingSource
import com.example.appsproduct.domain.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val apiService: ApiService, private val context: Context) {
    private val dao = AppDatabase.getDatabase(context).productDao()

    fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { ProductPagingSource(apiService) }
        ).flow
    }

    suspend fun getFavoriteProducts(): List<Product> {
        return apiService.getFavoriteProducts()
    }

    fun getListFavorites(): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { dao.getListFavorites() }
        ).flow
    }
}