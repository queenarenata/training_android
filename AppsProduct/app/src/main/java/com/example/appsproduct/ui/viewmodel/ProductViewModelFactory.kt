package com.example.appsproduct.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsproduct.data.repository.ProductRepository

class ProductViewModelFactory(private val repos: ProductRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repos) as T
    }
}