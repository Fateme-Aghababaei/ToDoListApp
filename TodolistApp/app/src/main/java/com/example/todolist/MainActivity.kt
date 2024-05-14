package com.example.todolist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.screens.LoginScreen
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                val userViewModel = viewModel<UserViewModel>()
                LoginScreen(Modifier, userViewModel) {
                    Log.v("mitra", it)
                }
            }
        }
    }
}