package com.example.todolist.models

import java.util.Date

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: Date,
    val isCompleted: Boolean,
    val priority: Int,
    val tags: List<Tag>
)

