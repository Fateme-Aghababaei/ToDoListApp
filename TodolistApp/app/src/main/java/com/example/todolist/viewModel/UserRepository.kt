package com.example.todolist.viewModel

import retrofit2.Response

interface UserRepository {
    suspend fun login(email: String, password: String): Pair<String, String>

    suspend fun signup(email: String, password: String): Pair<String, String>
}