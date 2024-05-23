package com.example.todolist.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todolist.models.Task
import com.example.todolist.screens.HomeScreen
import com.example.todolist.viewModel.TaskViewModel
import org.mockito.Mockito.when
import org.mockito.Mockito.mock

import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleTasks = listOf(
        Task(id = 1, title = "Task 1", description = "Description 1", due_date = "2024-05-23", tags = emptyList(), priority = 1, is_completed = false, user = "user1"),
        Task(id = 2, title = "Task 2", description = "Description 2", due_date = "2024-05-24", tags = emptyList(), priority = 2, is_completed = true, user = "user2")
    )

    @Test
    fun testFloatingActionButtonNavigatesToAddScreen() {
        val taskViewModel = mockk<TaskViewModel>(relaxed = true) {
            every { allTasks } returns MutableStateFlow(sampleTasks)
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            SetupNavGraph(navController = navController, taskViewModel = taskViewModel)
        }

        // Verify that we are on the HomeScreen
        composeTestRule.onNodeWithText("کارات").assertIsDisplayed()

        // Perform click on the Floating Action Button
        composeTestRule.onNodeWithContentDescription("Add").performClick()

        // Verify that we have navigated to the AddScreen
        composeTestRule.onNodeWithText("Add Task Screen").assertIsDisplayed()
    }

    @Composable
    fun SetupNavGraph(navController: NavHostController, taskViewModel: TaskViewModel) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    taskViewModel = taskViewModel,
                    token = "sample_token",
                    onProfileClicked = {},
                    onAddTaskClicked = { navController.navigate("add") },
                    refreshOnClick = {}
                )
            }
            composable("add") {
                // AddScreen Composable goes here
                Text("Add Task Screen")
            }
        }
    }
}
