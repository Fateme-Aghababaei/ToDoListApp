package com.example.todolist.viewModel

import android.util.Log
import com.example.todolist.utils.RetrofitInstance

class UserRepositoryImpl : UserRepository {
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
}