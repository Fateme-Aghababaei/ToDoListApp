package com.example.todolist.data

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {
    //TODO - add requests

    @FormUrlEncoded
    @POST("users/login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<Map<String, String>>
}