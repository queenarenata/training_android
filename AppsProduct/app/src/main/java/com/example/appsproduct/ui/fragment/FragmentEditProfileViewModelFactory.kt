package com.example.appsproduct.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsproduct.data.repository.UsersRepository

class FragmentEditProfileViewModelFactory(private val repos: UsersRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FragmentEditProfileViewModel(repos) as T
    }
}