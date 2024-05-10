package com.example.todolist.models

import android.provider.ContactsContract.CommonDataKinds.Email

data class User(
    val id: Int,
    val username: String,
    val name: String,
    val email: Email,
    val photoUrl: String
)
