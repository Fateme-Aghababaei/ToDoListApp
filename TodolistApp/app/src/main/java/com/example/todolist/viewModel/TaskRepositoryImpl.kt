package com.example.todolist.viewModel

import android.util.Log
import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import com.example.todolist.utils.RetrofitInstance

/**
 * Implementation of the [TaskRepository] interface using Retrofit for API calls.
 */
class TaskRepositoryImpl : TaskRepository {

    /**
     * Retrieves all tasks from the server.
     * @param token The authentication token for accessing the API.
     * @return A list of tasks.
     */
    override suspend fun getAllTasks(token: String): List<Task> {
        val response = RetrofitInstance.api.getAllTasks("Token $token")
        if (response.isSuccessful && response.body() != null) {
            val tasks: ArrayList<Task> = (response.body() as ArrayList<Task>?)!!
            return tasks
        } else {
            // throw Exception(response.code().toString())
            return emptyList()
        }
    }

    /**
     * Changes the completion status of a task.
     * @param token The authentication token for accessing the API.
     * @param id The ID of the task to be updated.
     * @param is_completed The new completion status of the task.
     * @return True if the task status is successfully updated, false otherwise.
     */
    override suspend fun changeTaskStatus(token: String, id: Int, is_completed: Boolean): Boolean {
        val response = RetrofitInstance.api.changeTaskStatus("Token $token", id, is_completed)
        return response.isSuccessful
    }

    /**
     * Adds a new task to the server.
     * @param token The authentication token for accessing the API.
     * @param task The task object to be added.
     * @return True if the task is successfully added, false otherwise.
     */
    override suspend fun addTask(token: String, task: Task): Boolean {
        val response = RetrofitInstance.api.addTask("Token $token", task)
        return response.isSuccessful
    }

    /**
     * Deletes a task from the server.
     * @param token The authentication token for accessing the API.
     * @param id The ID of the task to be deleted.
     * @return True if the task is successfully deleted, false otherwise.
     */
    override suspend fun deleteTask(token: String, id: Int): Boolean {
        val response = RetrofitInstance.api.deleteTask("Token $token", id)
        return response.isSuccessful
    }

    /**
     * Retrieves all tags from the server.
     * @param token The authentication token for accessing the API.
     * @return A list of tags.
     */
    override suspend fun getTags(token: String): List<Tag> {
        val response = RetrofitInstance.api.getTags("Token $token")
        if (response.isSuccessful && response.body() != null) {
            val tags: ArrayList<Tag> = (response.body() as ArrayList<Tag>?)!!
            return tags
        } else {
            // throw Exception(response.code().toString())
            return emptyList()
        }
    }

    /**
     * Adds a new tag to the server.
     * @param token The authentication token for accessing the API.
     * @param title The title of the tag to be added.
     * @return True if the tag is successfully added, false otherwise.
     */
    override suspend fun addTag(token: String, title: String): Boolean {
        val response = RetrofitInstance.api.addTag("Token $token", title)
        return response.isSuccessful
    }
}
