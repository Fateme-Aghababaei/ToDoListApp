package com.example.todolist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepositoryImpl()

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess: StateFlow<Boolean> get() = _logoutSuccess

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val pair = repository.login(email, password)
            _token.value = pair.first
            _username.value = pair.second
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            val pair = repository.signup(email, password)
            _token.value = pair.first
            _username.value = pair.second
        }
    }

    fun setTokenEmpty() {
        _token.value = ""
    }

    fun logout(token: String) {
        viewModelScope.launch {
            val success = repository.logout(token)
            Log.v("fatt", "success: $success")
            _logoutSuccess.value = success
        }
    }

    fun resetLogoutSuccess() {
        _logoutSuccess.value = false
    }
}