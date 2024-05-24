package com.example.todolist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing user-related operations and data.
 */
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
     * Logs in the user with the provided email and password.
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
     * Signs up a new user with the provided email and password.
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

    /**
     * Sets the authentication token to an empty string.
     */
    fun setTokenEmpty() {
        _token.value = ""
    }

    /**
     * Logs out the user identified by the given token.
     * @param token The authentication token of the user to be logged out.
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

    /**
     * Resets the logout success state to false.
     */
    fun resetLogoutSuccess() {
        _logoutSuccess.value = false
    }

    /**
     * Retrieves user information for the user identified by the given token.
     * @param token The authentication token of the user.
     */
    fun getUser(token: String) {
        viewModelScope.launch {
            val user = repository.getUser(token)
            _loggedInUser.value = user
        }
    }
}
