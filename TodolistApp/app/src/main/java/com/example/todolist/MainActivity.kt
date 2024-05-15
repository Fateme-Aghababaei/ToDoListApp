package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.navigation.Screens
import com.example.todolist.screens.HomeScreen
import com.example.todolist.screens.LoginScreen
import com.example.todolist.screens.SignupScreen
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewModel.UserViewModel

class MainActivity : ComponentActivity() {

    // Shared preferences
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
                val navController = rememberNavController()
                val userViewModel = viewModel<UserViewModel>()
                sharedPref.edit().putString(SHARED_PREFS_TOKEN, null).apply()

                NavHost(navController, startDestination = determineStartDestination()) {
                    // login
                    composable(route = Screens.ScreenLogin.route) {
                        LoginScreen(Modifier, userViewModel) { loggedInToken ->
                            // Handle login logic here
                            sharedPref.edit().putString(SHARED_PREFS_TOKEN, loggedInToken).apply()
                            // Navigate to home screen after successful login
                            navController.navigate(Screens.ScreenHome.route)
                        }
                    }

                    // signup
                    composable(route = Screens.ScreenSignup.route) {
                        SignupScreen()
                    }

                    // home
                    composable(route = Screens.ScreenHome.route) {
                        HomeScreen()
                    }

                    // TODO - Define other destinations here
                }
            }
        }
    }

    private fun determineStartDestination(): String {
        // Check if the user is already logged in
        return if (sharedPref.getString(SHARED_PREFS_TOKEN, null) != null) {
            Screens.ScreenHome.route
        } else {
            Screens.ScreenLogin.route
        }
    }
}
