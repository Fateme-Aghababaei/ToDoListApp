package com.example.todolist.utils

import com.example.todolist.data.APIInterface
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {
    val api : APIInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.Base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }
}