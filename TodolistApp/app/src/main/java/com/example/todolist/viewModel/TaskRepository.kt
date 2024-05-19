package com.example.todolist.viewModel

import com.example.todolist.models.Task

interface TaskRepository {
    suspend fun getAllTasks(token: String): List<Task>

    suspend fun changeTaskStatus(token: String, id: Int, is_completed: Boolean): Boolean

    suspend fun addTask(token: String, task: Task): Boolean
}