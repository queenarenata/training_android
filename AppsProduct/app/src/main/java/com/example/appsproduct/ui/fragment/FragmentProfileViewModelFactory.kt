package com.example.appsproduct.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsproduct.data.repository.UsersRepository

class FragmentProfileViewModelFactory(private val repository: UsersRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FragmentProfileViewModel(repository) as T
    }
}