package com.example.todolist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.User
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

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser
 /**
     * Initiates a login request with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val pair = repository.login(email, password)
            _token.value = pair.first
            _username.value = pair.second
        }
    }
/**
     * Initiates a signup request with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     */
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
 /**
     * Initiates a logout request with the provided token.
     * @param token The user's authentication token.
     */
    fun logout(token: String) {
        viewModelScope.launch {
            val success = repository.logout(token)
            _logoutSuccess.value = success
            _token.value = ""
            _username.value = ""
            _loggedInUser.value = null
        }
    }

    fun resetLogoutSuccess() {
        _logoutSuccess.value = false
    }
/**
     * Retrieves the user information associated with the provided token.
     * @param token The user's authentication token.
     */
    fun getUser(token: String) {
        viewModelScope.launch {
            val user = repository.getUser(token)
            _loggedInUser.value = user
        }
    }
}