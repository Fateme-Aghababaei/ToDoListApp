package com.example.todolist.viewModel

import com.example.todolist.models.Task

interface TaskRepository {
    suspend fun getAllTasks(token: String): List<Task>
}