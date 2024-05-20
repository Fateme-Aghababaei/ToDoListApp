package com.example.todolist.viewModel

import android.util.Log
import com.example.todolist.models.Task
import com.example.todolist.utils.RetrofitInstance

class TaskRepositoryImpl : TaskRepository {
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

    override suspend fun changeTaskStatus(token: String, id: Int, is_completed: Boolean): Boolean {
        val response = RetrofitInstance.api.changeTaskStatus("Token $token", id, is_completed)
        return response.isSuccessful
    }

    override suspend fun addTask(token: String, task: Task): Boolean {
        val response = RetrofitInstance.api.addTask("Token $token", task)
        return response.isSuccessful
    }

    override suspend fun deleteTask(token: String, id: Int): Boolean {
        Log.v("fatt", "token: $token")
        Log.v("fatt", "id: $id")
        val response = RetrofitInstance.api.deleteTask("Token $token", id)
        Log.v("fatt", "delete: ${response.isSuccessful}")
        Log.v("fatt", "delete: ${response.message()}")
        Log.v("fatt", "delete: ${response.code()}")
        return response.isSuccessful
    }
}