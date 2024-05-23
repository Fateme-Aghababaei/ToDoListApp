package com.example.todolist.viewModel

import android.util.Log
import com.example.todolist.models.Tag
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
        val response = RetrofitInstance.api.deleteTask("Token $token", id)
        return response.isSuccessful
    }

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

    override suspend fun addTag(token: String, title: String): Boolean {
        val response = RetrofitInstance.api.addTag("Token $token", title)
        Log.v("fatt", "body: ${response.body()}")
        Log.v("fatt", "body: ${response.isSuccessful}")
        Log.v("fatt", "body: ${response.message()}")
        Log.v("fatt", "body: ${response.code()}")
        return response.isSuccessful
    }
}