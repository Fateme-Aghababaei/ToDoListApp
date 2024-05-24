package com.example.todolist.viewModel

import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import com.example.todolist.utils.RetrofitInstance

class TaskRepositoryImpl : TaskRepository {
    /**
     * Retrieves all tasks associated with the provided token.
     * @param token The authentication token.
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
     * @param token The authentication token.
     * @param id The ID of the task.
     * @param is_completed The new completion status of the task.
     * @return True if the operation was successful, false otherwise.
     */
    override suspend fun changeTaskStatus(token: String, id: Int, is_completed: Boolean): Boolean {
        val response = RetrofitInstance.api.changeTaskStatus("Token $token", id, is_completed)
        return response.isSuccessful
    }

    /**
     * Adds a new task.
     * @param token The authentication token.
     * @param task The task to add.
     * @return True if the operation was successful, false otherwise.
     */
    override suspend fun addTask(token: String, task: Task): Boolean {
        val response = RetrofitInstance.api.addTask("Token $token", task)
        return response.isSuccessful
    }

    /**
     * Deletes a task with the specified ID.
     * @param token The authentication token.
     * @param id The ID of the task to delete.
     * @return True if the operation was successful, false otherwise.
     */
    override suspend fun deleteTask(token: String, id: Int): Boolean {
        val response = RetrofitInstance.api.deleteTask("Token $token", id)
        return response.isSuccessful
    }

    /**
     * Retrieves all tags associated with the provided token.
     * @param token The authentication token.
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
     * Adds a new tag with the specified title.
     * @param token The authentication token.
     * @param title The title of the tag to add.
     * @return True if the operation was successful, false otherwise.
     */
    override suspend fun addTag(token: String, title: String): Boolean {
        val response = RetrofitInstance.api.addTag("Token $token", title)
        return response.isSuccessful
    }
}