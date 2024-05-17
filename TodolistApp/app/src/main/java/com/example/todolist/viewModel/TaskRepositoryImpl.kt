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
        Log.v("fatt", "token: $token")
        Log.v("fatt", "id: $id")
        Log.v("fatt", "is_completed: $is_completed")
        val response = RetrofitInstance.api.changeTaskStatus("Token $token", id, is_completed)
        Log.v("fatt", "success: ${response.isSuccessful}")
        Log.v("fatt", "res: ${response.body()}")
        Log.v("fatt", "res: ${response.code()}")
        return response.isSuccessful
    }
}