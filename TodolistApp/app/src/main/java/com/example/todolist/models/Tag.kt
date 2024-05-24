
package com.example.todolist.models

/**
 * Data class representing a Tag.
 *
 * @property id The unique identifier of the tag.
 * @property title The title of the tag.
 * @property user The ID of the user associated with the tag.
 */
data class Tag(
    val id: Int,
    val title: String,
    val user: Int
)
