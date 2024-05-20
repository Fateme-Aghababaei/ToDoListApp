package com.example.todolist.data

import com.example.todolist.models.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface APIInterface {
    //TODO - add requests

    @FormUrlEncoded
    @POST("users/login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<Map<String, String>>

    @FormUrlEncoded
    @POST("users/signup/")
    suspend fun signup(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<Map<String, String>>

    @GET("task/get_tasks/")
    suspend fun getAllTasks(
        @Header("Authorization") authToken: String
    ): Response<List<Task>>

    @FormUrlEncoded
    @POST("task/edit_task/")
    suspend fun changeTaskStatus(
        @Header("Authorization") authToken: String,
        @Field("id") id: Int,
        @Field("is_completed") is_completed: Boolean
    ): Response<Map<String, Any>>


    @POST("task/add_task/")
    suspend fun addTask(
        @Header("Authorization") authToken: String,
        @Body task: Task
    ): Response<Map<String, String>>

    @FormUrlEncoded
    @POST("task/delete_task/")
    suspend fun deleteTask(
        @Header("Authorization") authToken: String,
        @Field("id") id: Int
    ): Response<Map<String, String>>
}