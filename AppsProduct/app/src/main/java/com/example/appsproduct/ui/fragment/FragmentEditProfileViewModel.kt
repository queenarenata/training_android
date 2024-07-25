package com.example.appsproduct.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsproduct.data.result.Result
import com.example.appsproduct.data.repository.UsersRepository
import com.example.appsproduct.domain.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FragmentEditProfileViewModel(private val repos: UsersRepository): ViewModel() {
    private var _user = MutableStateFlow<Result<Users>>(Result.Loading)
    val user = _user

    fun showData(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = Result.Loading
            try {
                repos.getUserFromDB(id).collect{
                    _user.value = it
                }
            }catch (e: Exception){
                _user.value = Result.Failure(e)
            }
        }
    }

    fun addUser(users: Users){
        viewModelScope.launch(Dispatchers.IO) {
            repos.addUser(users)
        }
    }

    fun resetUser(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repos.resetUser(id)
        }
    }
}