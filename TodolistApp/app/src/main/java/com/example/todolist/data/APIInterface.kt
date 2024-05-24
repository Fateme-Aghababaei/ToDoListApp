package com.example.todolist.data

import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import com.example.todolist.models.User
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
    /**
     * Sends a login request to the server.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A Response object containing a Map with login response data.
     */
    @FormUrlEncoded
    @POST("users/login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<Map<String, String>>

    /**
     * Sends a signup request to the server.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A Response object containing a Map with signup response data.
     */
    @FormUrlEncoded
    @POST("users/signup/")
    suspend fun signup(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<Map<String, String>>

    /**
     * Retrieves all tasks from the server.
     *
     * @param authToken The authentication token.
     * @return A Response object containing a list of Task objects.
     */
    @GET("task/get_tasks/")
    suspend fun getAllTasks(
        @Header("Authorization") authToken: String
    ): Response<List<Task>>

    /**
     * Changes the status of a task on the server.
     *
     * @param authToken The authentication token.
     * @param id The ID of the task to be updated.
     * @param is_completed The new completion status of the task.
     * @return A Response object containing a Map with the updated task data.
     */
    @FormUrlEncoded
    @POST("task/edit_task/")
    suspend fun changeTaskStatus(
        @Header("Authorization") authToken: String,
        @Field("id") id: Int,
        @Field("is_completed") is_completed: Boolean
    ): Response<Map<String, Any>>

    /**
     * Adds a new task to the server.
     *
     * @param authToken The authentication token.
     * @param task The Task object to be added.
     * @return A Response object containing a Map with the result of the add operation.
     */
    @POST("task/add_task/")
    suspend fun addTask(
        @Header("Authorization") authToken: String,
        @Body task: Task
    ): Response<Map<String, String>>

    /**
     * Deletes a task from the server.
     *
     * @param authToken The authentication token.
     * @param id The ID of the task to be deleted.
     * @return A Response object containing a Map with the result of the delete operation.
     */
    @FormUrlEncoded
    @POST("task/delete_task/")
    suspend fun deleteTask(
        @Header("Authorization") authToken: String,
        @Field("id") id: Int
    ): Response<Map<String, String>>

    /**
     * Retrieves all tags from the server.
     *
     * @param authToken The authentication token.
     * @return A Response object containing a list of Tag objects.
     */
    @GET("task/get_tags/")
    suspend fun getTags(
        @Header("Authorization") authToken: String
    ): Response<List<Tag>>

    /**
     * Logs out the user from the server.
     *
     * @param authToken The authentication token.
     * @return A Response object containing a Map with the result of the logout operation.
     */
    @POST("users/logout/")
    suspend fun logout(
        @Header("Authorization") authToken: String
    ): Response<Map<String, String>>

    /**
     * Retrieves user information from the server.
     *
     * @param authToken The authentication token.
     * @return A Response object containing a User object.
     */
    @GET("users/get_user/")
    suspend fun getUser(
        @Header("Authorization") authToken: String
    ): Response<User>

    /**
     * Adds a new tag to the server.
     *
     * @param authToken The authentication token.
     * @param title The title of the new tag.
     * @return A Response object containing a Map with the result of the add operation.
     */
    @FormUrlEncoded
    @POST("task/add_tag/")
    suspend fun addTag(
        @Header("Authorization") authToken: String,
        @Field("title") title: String
    ): Response<Map<String, String>>
}