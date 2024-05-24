package com.example.todolist.viewModel

import android.util.Log
import com.example.todolist.models.User
import com.example.todolist.utils.RetrofitInstance

/**
 * Implementation of the UserRepository interface for user-related operations.
 */
class UserRepositoryImpl : UserRepository {

    /**
     * Logs in the user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A Pair consisting of the authentication token and username, or an empty Pair if login fails.
     */
    override suspend fun login(email: String, password: String): Pair<String, String> {
        val response = RetrofitInstance.api.login(email, password)
        if (response.isSuccessful && response.body() != null) {
            val res = response.body()
            val pair = Pair(res?.get("token") ?: "", res?.get("username") ?: "")
            return pair
        } else {
            Log.v("faat", response.headers().toString())
            return Pair("", "")
        }
    }

    /**
     * Signs up a new user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A Pair consisting of the authentication token and username, or throws an Exception if signup fails.
     */
    override suspend fun signup(email: String, password: String): Pair<String, String> {
        val response = RetrofitInstance.api.signup(email, password)
        if (response.isSuccessful && response.body() != null) {
            val res = response.body()
            val pair = Pair(res?.get("token") ?: "", res?.get("username") ?: "")
            return pair
        } else {
            throw Exception(response.code().toString())
        }
    }

    /**
     * Logs out the user identified by the given token.
     * @param token The authentication token of the user to be logged out.
     * @return true if the logout operation was successful, false otherwise.
     */
    override suspend fun logout(token: String): Boolean {
        val response = RetrofitInstance.api.logout("Token $token")
        return response.isSuccessful
    }

    /**
     * Retrieves user information for the user identified by the given token.
     * @param token The authentication token of the user.
     * @return The User object representing the user, or null if no user is found.
     */
    override suspend fun getUser(token: String): User? {
        val response = RetrofitInstance.api.getUser("Token $token")
        if (response.isSuccessful){
            val user = response.body()
            Log.v("fatt", "user: $user")
            return user
        }
        else {
            Log.v("fatt", "user: else")
            return null
        }
    }
}
