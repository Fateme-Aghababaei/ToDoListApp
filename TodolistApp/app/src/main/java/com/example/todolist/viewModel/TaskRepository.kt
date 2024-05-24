package com.example.todolist.viewModel

import com.example.todolist.models.Tag
import com.example.todolist.models.Task

interface TaskRepository {
      /**
     * Retrieves all tasks associated with the provided token.
     * @param token The authentication token.
     * @return A list of tasks.
     */
    suspend fun getAllTasks(token: String): List<Task>
 /**
     * Changes the completion status of a task.
     * @param token The authentication token.
     * @param id The ID of the task.
     * @param is_completed The new completion status of the task.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun changeTaskStatus(token: String, id: Int, is_completed: Boolean): Boolean
/**
     * Adds a new task.
     * @param token The authentication token.
     * @param task The task to add.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun addTask(token: String, task: Task): Boolean
    /**
     * Deletes a task with the specified ID.
     * @param token The authentication token.
     * @param id The ID of the task to delete.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun deleteTask(token: String, id: Int): Boolean
 /**
     * Retrieves all tags associated with the provided token.
     * @param token The authentication token.
     * @return A list of tags.
     */
    suspend fun getTags(token: String): List<Tag>
  /**
     * Adds a new tag with the specified title.
     * @param token The authentication token.
     * @param title The title of the tag to add.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun addTag(token: String, title: String): Boolean
}