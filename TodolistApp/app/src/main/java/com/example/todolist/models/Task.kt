package com.example.todolist.models

/**
 * Data class representing a Task.
 *
 * @property id The unique identifier of the task.
 * @property title The title of the task.
 * @property description The description of the task.
 * @property due_date The due date of the task.
 * @property is_completed Indicates whether the task is completed or not.
 * @property priority The priority of the task.
 * @property tags The list of tags associated with the task.
 * @property user The ID of the user associated with the task.
 */
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val due_date: String,
    var is_completed: Boolean,
    val priority: Int,
    val tags: List<Tag>,
    val user: Int?
)
