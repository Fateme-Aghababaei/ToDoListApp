package com.example.todolist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing tasks and tags.
 */
class TaskViewModel : ViewModel() {
    private val repository = TaskRepositoryImpl()

    // StateFlow for holding the list of all tasks
    private val _allTasks = MutableStateFlow(listOf<Task>())
    val allTasks: StateFlow<List<Task>> = _allTasks

    // StateFlow for indicating the status of changing a task's completion status
    private val _changeTaskStatus = MutableStateFlow(false)
    val changeTaskStatus: StateFlow<Boolean> = _changeTaskStatus

    // StateFlow for indicating the status of adding a new task
    private val _addTaskStatus = MutableStateFlow(false)
    val addTaskStatus: StateFlow<Boolean> = _addTaskStatus

    // StateFlow for indicating the status of deleting a task
    private val _deleteTaskStatus = MutableStateFlow(false)
    val deleteTaskStatus: StateFlow<Boolean> = _deleteTaskStatus

    // StateFlow for holding the list of all tags
    private val _allTags = MutableStateFlow(listOf<Tag>())
    val allTags: StateFlow<List<Tag>> = _allTags

    /**
     * Retrieves all tasks from the server.
     * @param token The authentication token for accessing the API.
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
     * Changes the completion status of a task.
     * @param token The authentication token for accessing the API.
     * @param id The ID of the task to be updated.
     * @param is_completed The new completion status of the task.
     */
    fun changeTaskStatus(token: String, id: Int, is_completed: Boolean) {
        viewModelScope.launch {
            val success = repository.changeTaskStatus(token, id, is_completed)
            _changeTaskStatus.value = success
        }
    }

    /**
     * Adds a new task to the server.
     * @param token The authentication token for accessing the API.
     * @param task The task object to be added.
     */
    fun addTask(token: String, task: Task) {
        viewModelScope.launch {
            val success = repository.addTask(token, task)
            _changeTaskStatus.value = success
        }
    }

    /**
     * Deletes a task from the server.
     * @param token The authentication token for accessing the API.
     * @param id The ID of the task to be deleted.
     */
    fun deleteTask(token: String, id: Int) {
        viewModelScope.launch {
            val success = repository.deleteTask(token, id)
            _deleteTaskStatus.value = success
        }
    }

    /**
     * Retrieves all tags from the server.
     * @param token The authentication token for accessing the API.
     */
    fun getTags(token: String) {
        viewModelScope.launch {
            val tagsList = repository.getTags(token)
            _allTags.value = tagsList
        }
    }

    /**
     * Adds a new tag to the server.
     * @param token The authentication token for accessing the API.
     * @param title The title of the tag to be added.
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
