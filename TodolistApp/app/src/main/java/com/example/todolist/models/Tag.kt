package com.example.todolist.models;

/**
 * Represents a tag associated with a task.
 * 
 * @property id The unique identifier of the tag.
 * @property title The title of the tag.
 * @property user The ID of the user who owns this tag.
 */
data class Tag(
    val id: Int,
    val title: String,
    val user: Int
)
