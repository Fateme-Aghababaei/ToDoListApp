package com.example.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.navigation.Screens
import com.example.todolist.screens.*
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewModel.TaskViewModel
import com.example.todolist.viewModel.UserViewModel

/**
 * MainActivity for the ToDoList application.
 * This activity serves as the entry point for the application and handles navigation between different screens.
 */
class MainActivity : ComponentActivity() {

    // Shared preferences
    private val SHARED_PREFS_TOKEN = "token"
    private val sharedPref by lazy {
        getSharedPreferences(
            "${BuildConfig.APPLICATION_ID}_sharedPreferences",
            Context.MODE_PRIVATE
        )
    }

    /**
     * onCreate function is called when the activity is starting.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //sharedPref.edit().putString(SHARED_PREFS_TOKEN, "").apply()

        // Extract file content if the activity was started with ACTION_VIEW intent
        val fileContent: String? = intent?.takeIf { it.action == Intent.ACTION_VIEW }?.data?.let { uri ->
            contentResolver.openInputStream(uri)?.bufferedReader().use { reader ->
                reader?.readText()
            }
        }

        setContent {
            ToDoListTheme {
                val navController = rememberNavController()
                val userViewModel = viewModel<UserViewModel>()
                val taskViewModel = viewModel<TaskViewModel>()

                NavHost(navController, startDestination = determineStartDestination(fileContent)) {
                    // Login screen
                    composable(route = Screens.ScreenLogin.route) {
                        LoginScreen(Modifier, userViewModel, { loggedInToken ->
                            sharedPref.edit().putString(SHARED_PREFS_TOKEN, loggedInToken).apply()
                            if (sharedPref.getString(SHARED_PREFS_TOKEN, "") != "")
                                navController.navigate(Screens.ScreenHome.route)
                        }, {
                            navController.navigate(Screens.ScreenSignup.route)
                        })
                    }

                    // Signup screen
                    composable(route = Screens.ScreenSignup.route) {
                        SignupScreen(Modifier, userViewModel, { signedUpToken ->
                            sharedPref.edit().putString(SHARED_PREFS_TOKEN, signedUpToken).apply()
                            if (sharedPref.getString(SHARED_PREFS_TOKEN, "") != "")
                                navController.navigate(Screens.ScreenHome.route)
                        }, {

                            navController.navigate(Screens.ScreenLogin.route)
                        })
                    }

                    // Home screen
                    composable(route = Screens.ScreenHome.route) {
                        HomeScreen(
                            modifier = Modifier,
                            taskViewModel = taskViewModel,
                            userViewModel = userViewModel,
                            token = sharedPref.getString(SHARED_PREFS_TOKEN, "").toString(),
                            onAddTaskClicked = {
                                navController.navigate(Screens.ScreenAdd.route)
                            },
                            refreshOnClick = {
                                taskViewModel.getAllTasks(
                                    sharedPref.getString(SHARED_PREFS_TOKEN, "").toString()
                                )
                            },
                            onLogout = {
                                sharedPref.edit().putString(SHARED_PREFS_TOKEN, "").apply()
                                val tokenCleared = sharedPref.getString(SHARED_PREFS_TOKEN, "").isNullOrEmpty()
                                userViewModel.setTokenEmpty()
                                navController.navigate(Screens.ScreenLogin.route)
                            }
                        )
                    }

                    // Add task screen
                    composable(
                        route = Screens.ScreenAdd.route,
                        arguments = listOf(navArgument("initialTitle") { defaultValue = "" })
                    ) { backStackEntry ->
                        val initialTitle = backStackEntry.arguments?.getString("initialTitle") ?: ""
                        AddScreen(
                            modifier = Modifier,
                            taskViewModel = taskViewModel,
                            token = sharedPref.getString(SHARED_PREFS_TOKEN, "").toString(),
                            onCancelClicked = {
                                navController.navigate(Screens.ScreenHome.route)
                            },
                            onAddTaskClicked = {
                                taskViewModel.addTask(
                                    sharedPref.getString(SHARED_PREFS_TOKEN, "").toString(), it
                                )
                                navController.navigate(Screens.ScreenHome.route)
                            },
                            initialTitle = fileContent ?: initialTitle // Use file content if available
                        )
                    }
                }
            }
        }
    }


     /**
     * Determines the start destination based on the presence of file content and login status.
     */
    private fun determineStartDestination(fileContent: String?): String {
        // Navigate to AddScreen if file content is available
        return when {
            fileContent != null -> Screens.ScreenAdd.route
            sharedPref.getString(SHARED_PREFS_TOKEN, "").isNullOrEmpty() -> Screens.ScreenLogin.route
            else -> Screens.ScreenHome.route
        }
    }
}
