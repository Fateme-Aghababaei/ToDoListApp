package com.example.todolist.viewModel

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

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val pair = repository.login(email, password)
            _token.value = pair.first
            _username.value = pair.second
        }
    }
}