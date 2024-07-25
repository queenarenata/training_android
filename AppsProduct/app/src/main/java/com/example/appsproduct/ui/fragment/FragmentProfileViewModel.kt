package com.example.appsproduct.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsproduct.data.result.Result
import com.example.appsproduct.data.repository.UsersRepository
import com.example.appsproduct.domain.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FragmentProfileViewModel(private val repos: UsersRepository): ViewModel() {
    private var _dataUser = MutableStateFlow<Result<Users>>(Result.Loading)
    val dataUser = _dataUser

    fun getUser(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _dataUser.value = Result.Loading
            try {
                repos.getUser(id).collect {
                    _dataUser.value = it
                }
            }catch (e: Exception){
                _dataUser.value = Result.Failure(e)
            }
        }
    }
}