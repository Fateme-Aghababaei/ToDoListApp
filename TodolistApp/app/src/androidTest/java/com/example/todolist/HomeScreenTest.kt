//package com.example.todolist
//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.performClick
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.todolist.screens.HomeScreen
//import com.example.todolist.viewModel.TaskViewModel
//import com.example.todolist.viewModel.UserViewModel
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class HomeScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var userViewModel: UserViewModel
//    private lateinit var taskViewModel: TaskViewModel
//
//    @Before
//    fun setUp() {
//        composeTestRule
//        val context = composeTestRule.activity.applicationContext
//        sharedPreferences = context.getSharedPreferences("your_preferences_name", Context.MODE_PRIVATE)
//
//        userViewModel = Mockito.mock(UserViewModel::class.java)
//        taskViewModel = Mockito.mock(TaskViewModel::class.java)
//
//
//        // Assuming you have a way to set the token in the ViewModel for testing
//        Mockito.when(userViewModel.logoutSuccess).thenReturn(MutableStateFlow(true))
//    }
//
//    @Test
//    fun testLogoutRemovesToken() {
//        composeTestRule.setContent {
//            HomeScreen(
//                taskViewModel = taskViewModel,
//                userViewModel = userViewModel,
//                token = "dummy_token",
//                onAddTaskClicked = {},
//                refreshOnClick = {},
//                onLogout = {}
//            )
//        }
//
//        // Perform click on the logout button
//        composeTestRule.onNodeWithText("خروج از حساب کاربری").performClick()
//
//        // Check if the token is removed from SharedPreferences
//        val token = sharedPreferences.getString("token_key", null)
//        assert(token == null)
//    }
//}