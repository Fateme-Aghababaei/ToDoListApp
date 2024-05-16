package com.example.todolist.viewModel

import android.util.Log
import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import com.example.todolist.utils.RetrofitInstance

class TaskRepositoryImpl: TaskRepository {
    override suspend fun getAllTasks(token: String): List<Task> {
        val response = RetrofitInstance.api.getAllTasks("token $token")
        if (response.isSuccessful && response.body() != null) {
//            val taskDTOList = response.body()!!
//            val taskList = mutableListOf<Task>()
//            // Mapping DTO objects to Task objects
//            for (taskDTO in taskDTOList) {
//                val task = Task(
//                    id = taskDTO.id,
//                    title = taskDTO.title,
//                    description = taskDTO.description,
//                    due_date = taskDTO.due_date,
//                    is_completed = taskDTO.is_completed,
//                    priority = taskDTO.priority,
//                    tags = taskDTO.tags.map { tagDTO ->
//                        Tag(
//                            id = tagDTO.id,
//                            title = tagDTO.title,
//                            user = taskDTO.user
//                        )
//                    },
//                    user = taskDTO.user
//                )
//                taskList.add(task)
//            }
//            return taskList

            val tasks : ArrayList<Task> = (response.body() as ArrayList<Task>?)!!
            return tasks
        } else {
            // throw Exception(response.code().toString())
            return emptyList()
        }
    }
}