package com.example.todolist.models

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

