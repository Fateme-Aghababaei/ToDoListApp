package com.example.todolist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {
    private val repository = TaskRepositoryImpl()

    private val _allTasks = MutableStateFlow(listOf<Task>())
    val allTasks : StateFlow<List<Task>> = _allTasks
    fun getAllTasks(token: String) {
        viewModelScope.launch {
            val tasksList = repository.getAllTasks(token)
            _allTasks.value = tasksList
        }
    }
}