package com.example.todolist.viewModel

import com.example.todolist.models.User
import com.example.todolist.utils.RetrofitInstance

class UserRepositoryImpl : UserRepository {
    /**
     * Logs in the user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A pair containing the user's authentication token and an optional error message.
     */
    override suspend fun login(email: String, password: String): Pair<String, String> {
        val response = RetrofitInstance.api.login(email, password)
        if (response.isSuccessful && response.body() != null) {
            val res = response.body()
            val pair = Pair(res?.get("token") ?: "", res?.get("username") ?: "")
            return pair
        } else {
            return Pair("", "")
        }
    }

    /**
     * Signs up the user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A pair containing the user's authentication token and an optional error message.
     * @throws Exception if the signup request fails.
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
     * Logs out the user with the specified token.
     * @param token The user's authentication token.
     * @return A boolean indicating whether the logout operation was successful.
     */
    override suspend fun logout(token: String): Boolean {
        val response = RetrofitInstance.api.logout("Token $token")
        return response.isSuccessful
    }

    /**
     * Retrieves the user information associated with the provided token.
     * @param token The user's authentication token.
     * @return The user object if found, or null if no user is associated with the token.
     */
    override suspend fun getUser(token: String): User? {
        val response = RetrofitInstance.api.getUser("Token $token")
        if (response.isSuccessful) {
            val user = response.body()
            return user
        } else {
            return null
        }
    }
}