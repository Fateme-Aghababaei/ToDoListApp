package com.example.todolist.viewModel

import android.util.Log
import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import com.example.todolist.utils.RetrofitInstance

class TaskRepositoryImpl: TaskRepository {
    override suspend fun getAllTasks(token: String): List<Task> {
        val response = RetrofitInstance.api.getAllTasks(token)
        if (response.isSuccessful && response.body() != null) {
            val taskDTOList = response.body()!! // Assuming the response body is a list of DTO objects representing tasks
            val taskList = mutableListOf<Task>()

            // Mapping DTO objects to Task objects
            for (taskDTO in taskDTOList) {
                val task = Task(
                    id = taskDTO.id,
                    title = taskDTO.title,
                    description = taskDTO.description,
                    dueDate = taskDTO.dueDate,
                    isCompleted = taskDTO.isCompleted,
                    priority = taskDTO.priority,
                    tags = taskDTO.tags.map { tagDTO ->
                        Tag(
                            id = tagDTO.id,
                            title = tagDTO.title
                        )
                    }
                )
                taskList.add(task)
            }
            Log.v("fatt", taskList.toString())
            return taskList
        } else {
            // throw Exception(response.code().toString())
            Log.v("fatt", "empty list")
            return emptyList()
        }
    }
}