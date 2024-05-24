package com.example.todolist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val repository = TaskRepositoryImpl()

    private val _allTasks = MutableStateFlow(listOf<Task>())
    val allTasks: StateFlow<List<Task>> = _allTasks

    private val _changeTaskStatus = MutableStateFlow(false)
    val changeTaskStatus: StateFlow<Boolean> = _changeTaskStatus

    private val _addTaskStatus = MutableStateFlow(false)
    val addTaskStatus: StateFlow<Boolean> = _addTaskStatus

    private val _deleteTaskStatus = MutableStateFlow(false)
    val deleteTaskStatus: StateFlow<Boolean> = _deleteTaskStatus

    private val _allTags = MutableStateFlow(listOf<Tag>())
    val allTags: StateFlow<List<Tag>> = _allTags

    fun getAllTasks(token: String) {
        viewModelScope.launch {
            val tasksList = repository.getAllTasks(token)
            _allTasks.value = tasksList.sortedWith(
                compareBy<Task> { it.is_completed }
                    .thenBy { it.due_date }
            )
        }
    }

    fun changeTaskStatus(token: String, id: Int, is_completed: Boolean) {
        viewModelScope.launch {
            val success = repository.changeTaskStatus(token, id, is_completed)
            _changeTaskStatus.value = success
        }
    }

    fun addTask(token: String, task: Task) {
        viewModelScope.launch {
            val success = repository.addTask(token, task)
            _changeTaskStatus.value = success
        }
    }

    fun deleteTask(token: String, id: Int) {
        viewModelScope.launch {
            val success = repository.deleteTask(token, id)
            _deleteTaskStatus.value = success
        }
    }

    fun getTags(token: String) {
        viewModelScope.launch {
            val tagsList = repository.getTags(token)
            _allTags.value = tagsList
        }
    }

    fun addTag(token: String, title: String) {
        viewModelScope.launch {
            val success = repository.addTag(token, title)
            if (success) {
                getTags(token)
            }
        }
    }
}