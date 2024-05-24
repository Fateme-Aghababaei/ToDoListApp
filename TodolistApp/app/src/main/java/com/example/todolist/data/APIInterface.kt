package com.example.todolist.data;

import com.example.todolist.models.Tag;
import com.example.todolist.models.Task;
import com.example.todolist.models.User;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface APIInterface {

    /**
     * Logs in a user with the provided email and password.
     * 
     * @param email The user's email address.
     * @param password The user's password.
     * @return A Response containing a map with authentication details.
     */
    @FormUrlEncoded
    @POST("users/login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<Map<String, String>>

    /**
     * Signs up a new user with the provided email and password.
     * 
     * @param email The user's email address.
     * @param password The user's password.
     * @return A Response containing a map with user details.
     */
    @FormUrlEncoded
    @POST("users/signup/")
    suspend fun signup(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<Map<String, String>>

    /**
     * Retrieves all tasks for the authenticated user.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @return A Response containing a list of Task objects.
     */
    @GET("task/get_tasks/")
    suspend fun getAllTasks(
        @Header("Authorization") authToken: String
    ): Response<List<Task>>

    /**
     * Changes the status of a task.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @param id The ID of the task to be updated.
     * @param is_completed The new completion status of the task.
     * @return A Response containing a map with status details.
     */
    @FormUrlEncoded
    @POST("task/edit_task/")
    suspend fun changeTaskStatus(
        @Header("Authorization") authToken: String,
        @Field("id") id: Int,
        @Field("is_completed") is_completed: Boolean
    ): Response<Map<String, Any>>

    /**
     * Adds a new task.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @param task The Task object to be added.
     * @return A Response containing a map with status details.
     */
    @POST("task/add_task/")
    suspend fun addTask(
        @Header("Authorization") authToken: String,
        @Body task: Task
    ): Response<Map<String, String>>

    /**
     * Deletes a task.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @param id The ID of the task to be deleted.
     * @return A Response containing a map with status details.
     */
    @FormUrlEncoded
    @POST("task/delete_task/")
    suspend fun deleteTask(
        @Header("Authorization") authToken: String,
        @Field("id") id: Int
    ): Response<Map<String, String>>

    /**
     * Retrieves all tags for the authenticated user.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @return A Response containing a list of Tag objects.
     */
    @GET("task/get_tags/")
    suspend fun getTags(
        @Header("Authorization") authToken: String
    ): Response<List<Tag>>

    /**
     * Logs out the authenticated user.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @return A Response containing a map with status details.
     */
    @POST("users/logout/")
    suspend fun logout(
        @Header("Authorization") authToken: String
    ): Response<Map<String, String>>

    /**
     * Retrieves the details of the authenticated user.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @return A Response containing the User object.
     */
    @GET("users/get_user/")
    suspend fun getUser(
        @Header("Authorization") authToken: String
    ): Response<User>

    /**
     * Adds a new tag.
     * 
     * @param authToken The authorization token for the authenticated user.
     * @param title The title of the new tag.
     * @return A Response containing a map with status details.
     */
    @FormUrlEncoded
    @POST("task/add_tag/")
    suspend fun addTag(
        @Header("Authorization") authToken: String,
        @Field("title") title: String
    ): Response<Map<String, String>>
}
