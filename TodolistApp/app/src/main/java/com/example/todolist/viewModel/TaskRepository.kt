package com.example.todolist.viewModel

import com.example.todolist.models.Tag
import com.example.todolist.models.Task

interface TaskRepository {
    suspend fun getAllTasks(token: String): List<Task>

    suspend fun changeTaskStatus(token: String, id: Int, is_completed: Boolean): Boolean

    suspend fun addTask(token: String, task: Task): Boolean

    suspend fun deleteTask(token: String, id: Int): Boolean

    suspend fun getTags(token: String): List<Tag>

    suspend fun addTag(token: String, title: String): Boolean
}