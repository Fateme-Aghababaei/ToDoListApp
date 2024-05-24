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
 /**
     * Retrieves all tasks associated with the provided token and updates the [allTasks] state flow.
     * @param token The authentication token.
     */
    fun getAllTasks(token: String) {
        viewModelScope.launch {
            val tasksList = repository.getAllTasks(token)
            _allTasks.value = tasksList.sortedWith(
                compareBy<Task> { it.is_completed }
                    .thenBy { it.due_date }
            )
        }
    }
   /**
     * Changes the completion status of a task with the specified ID and updates the [changeTaskStatus] state flow.
     * @param token The authentication token.
     * @param id The ID of the task.
     * @param is_completed The new completion status of the task.
     */
    fun changeTaskStatus(token: String, id: Int, is_completed: Boolean) {
        viewModelScope.launch {
            val success = repository.changeTaskStatus(token, id, is_completed)
            _changeTaskStatus.value = success
        }
    }
/**
     * Adds a new task and updates the [addTaskStatus] state flow.
     * @param token The authentication token.
     * @param task The task to add.
     */
    fun addTask(token: String, task: Task) {
        viewModelScope.launch {
            val success = repository.addTask(token, task)
            _changeTaskStatus.value = success
        }
    }
 /**
     * Deletes a task with the specified ID and updates the [deleteTaskStatus] state flow.
     * @param token The authentication token.
     * @param id The ID of the task to delete.
     */
    fun deleteTask(token: String, id: Int) {
        viewModelScope.launch {
            val success = repository.deleteTask(token, id)
            _deleteTaskStatus.value = success
        }
    }
    /**
     * Retrieves all tags associated with the provided token and updates the [allTags] state flow.
     * @param token The authentication token.
     */
    fun getTags(token: String) {
        viewModelScope.launch {
            val tagsList = repository.getTags(token)
            _allTags.value = tagsList
        }
    }
 /**
     * Adds a new tag with the specified title, then refreshes the list of tags.
     * @param token The authentication token.
     * @param title The title of the tag to add.
     */
    fun addTag(token: String, title: String) {
        viewModelScope.launch {
            val success = repository.addTag(token, title)
            if (success) {
                getTags(token)
            }
        }
    }
}