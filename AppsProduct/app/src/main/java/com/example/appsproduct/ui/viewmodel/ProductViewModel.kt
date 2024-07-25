package com.example.appsproduct.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.appsproduct.data.local.entity.ProductEntity
import com.example.appsproduct.data.repository.ProductRepository
import com.example.appsproduct.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val products: Flow<PagingData<Product>> = repository.getProducts().cachedIn(viewModelScope)
    val listFavorites: Flow<PagingData<ProductEntity>> = repository.getListFavorites().cachedIn(viewModelScope)

    fun getFavoriteProducts(): Flow<List<Product>> {
        return flow {
            val favorites = repository.getFavoriteProducts()
            emit(favorites)
        }.flowOn(Dispatchers.IO)
    }
}