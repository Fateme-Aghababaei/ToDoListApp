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
import com.example.todolist.screens.AddScreen
import com.example.todolist.screens.HomeScreen
import com.example.todolist.screens.LoginScreen
import com.example.todolist.screens.ProfileScreen
import com.example.todolist.screens.SignupScreen
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewModel.TaskViewModel
import com.example.todolist.viewModel.UserViewModel

class MainActivity : ComponentActivity() {

    // Shared preferences
    private val SHARED_PREFS_TOKEN = "token"
    private val sharedPref by lazy {
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
                val taskViewModel = viewModel<TaskViewModel>()
                // TODO - delete line after adding the logic to handle logout
                // sharedPref.edit().putString(SHARED_PREFS_TOKEN, null).apply()

                NavHost(navController, startDestination = determineStartDestination()) {
                    // login
                    composable(route = Screens.ScreenLogin.route) {
                        LoginScreen(Modifier, userViewModel, { loggedInToken ->
                            sharedPref.edit().putString(SHARED_PREFS_TOKEN, loggedInToken).apply()
                            navController.navigate(Screens.ScreenHome.route)
                        }, {
                            // Handle signup navigation here
                            navController.navigate(Screens.ScreenSignup.route)
                        })
                    }

                    // signup
                    composable(route = Screens.ScreenSignup.route) {
                        SignupScreen(Modifier, userViewModel, { signedUpToken ->
                            sharedPref.edit().putString(SHARED_PREFS_TOKEN, signedUpToken).apply()
                            navController.navigate(Screens.ScreenHome.route)
                        }, {
                            // Handle login navigation here
                            navController.navigate(Screens.ScreenLogin.route)
                        })
                    }

                    // home
                    composable(route = Screens.ScreenHome.route) {

                        HomeScreen(
                            modifier = Modifier, taskViewModel = taskViewModel,
                            token = sharedPref.getString(SHARED_PREFS_TOKEN, "").toString(),
                            onProfileClicked = {
                                navController.navigate(Screens.ScreenProfile.route)
                            },
                            onAddTaskClicked = {
                                navController.navigate(Screens.ScreenAdd.route)
                            },
                            refreshOnClick = {
                                taskViewModel.getAllTasks(sharedPref.getString(SHARED_PREFS_TOKEN, "").toString())
                            }
                        )
                    }

                    // add task
                    composable(route = Screens.ScreenAdd.route) {
                        AddScreen(modifier = Modifier, onCancelClicked = {
                            navController.navigate(Screens.ScreenHome.route)
                        }, onAddTaskClicked = {
                            taskViewModel.addTask(sharedPref.getString(SHARED_PREFS_TOKEN, "").toString(),it)
                            navController.navigate(Screens.ScreenHome.route)
                        })
                    }

                    // profile
                    composable(route = Screens.ScreenProfile.route) {
                        ProfileScreen()
                    }
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
