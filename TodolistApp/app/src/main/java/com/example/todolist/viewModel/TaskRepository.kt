package com.example.todolist.viewModel

import com.example.todolist.models.Tag
import com.example.todolist.models.Task

/**
 * Interface defining methods for interacting with tasks and tags.
 */
interface TaskRepository {
    /**
     * Retrieves all tasks from the server.
     * @param token The authentication token for accessing the API.
     * @return A list of tasks.
     */
    suspend fun getAllTasks(token: String): List<Task>

    /**
     * Changes the completion status of a task.
     * @param token The authentication token for accessing the API.
     * @param id The ID of the task to be updated.
     * @param is_completed The new completion status of the task.
     * @return True if the task status is successfully updated, false otherwise.
     */
    suspend fun changeTaskStatus(token: String, id: Int, is_completed: Boolean): Boolean

    /**
     * Adds a new task to the server.
     * @param token The authentication token for accessing the API.
     * @param task The task object to be added.
     * @return True if the task is successfully added, false otherwise.
     */
    suspend fun addTask(token: String, task: Task): Boolean

    /**
     * Deletes a task from the server.
     * @param token The authentication token for accessing the API.
     * @param id The ID of the task to be deleted.
     * @return True if the task is successfully deleted, false otherwise.
     */
    suspend fun deleteTask(token: String, id: Int): Boolean

    /**
     * Retrieves all tags from the server.
     * @param token The authentication token for accessing the API.
     * @return A list of tags.
     */
    suspend fun getTags(token: String): List<Tag>

    /**
     * Adds a new tag to the server.
     * @param token The authentication token for accessing the API.
     * @param title The title of the tag to be added.
     * @return True if the tag is successfully added, false otherwise.
     */
    suspend fun addTag(token: String, title: String): Boolean
}
