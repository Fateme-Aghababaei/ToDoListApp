package com.example.todolist.viewModel

import com.example.todolist.models.Task
import com.example.todolist.utils.RetrofitInstance

class TaskRepositoryImpl : TaskRepository {
    override suspend fun getAllTasks(token: String): List<Task> {
        val response = RetrofitInstance.api.getAllTasks("token $token")
        if (response.isSuccessful && response.body() != null) {
            val tasks: ArrayList<Task> = (response.body() as ArrayList<Task>?)!!
            return tasks
        } else {
            // throw Exception(response.code().toString())
            return emptyList()
        }
    }
}