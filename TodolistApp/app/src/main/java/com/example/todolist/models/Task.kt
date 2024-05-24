package com.example.todolist.models;

/**
 * Represents a task in the to-do list application.
 * 
 * @property id The unique identifier of the task.
 * @property title The title of the task.
 * @property description A detailed description of the task.
 * @property due_date The due date for the task in a string format.
 * @property is_completed The completion status of the task.
 * @property priority The priority level of the task (e.g., 1 for high, 2 for medium, 3 for low).
 * @property tags A list of tags associated with the task.
 * @property user The ID of the user who created the task. Nullable to allow tasks without a specific user.
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
