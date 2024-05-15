package com.example.todolist

import android.content.Context
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
    private val SHARED_PREFS_TOKEN = "token"
    val sharedPref by lazy {
        getSharedPreferences(
            "${BuildConfig.APPLICATION_ID}_sharedPreferences",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                val userViewModel = viewModel<UserViewModel>()
                LoginScreen(Modifier, userViewModel) {
                    sharedPref.edit().putString(SHARED_PREFS_TOKEN, it).apply()
//                    val x = sharedPref.getString(SHARED_PREFS_TOKEN, "")
                }
            }
        }
    }
}
