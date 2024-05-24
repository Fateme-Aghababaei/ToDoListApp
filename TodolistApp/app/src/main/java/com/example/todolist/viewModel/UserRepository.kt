package com.example.todolist.viewModel

import com.example.todolist.models.User
import retrofit2.Response

interface UserRepository {
    suspend fun login(email: String, password: String): Pair<String, String>

    suspend fun signup(email: String, password: String): Pair<String, String>

    suspend fun logout(token: String): Boolean

    suspend fun getUser(token: String): User?
}