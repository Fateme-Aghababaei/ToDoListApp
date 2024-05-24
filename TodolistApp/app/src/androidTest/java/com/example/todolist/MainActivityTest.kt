package com.example.todolist

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationToLoginScreen() {
        composeTestRule.onNodeWithText("ورود به حساب کاربری").assertIsDisplayed()
    }

    @Test
    fun testNavigationToSignupScreen() {
        // Wait for the UI to be fully rendered
        composeTestRule.waitForIdle()

        // Print out the semantics tree for debugging
        composeTestRule.onRoot().printToLog("TEST_TAG")

        composeTestRule.onNodeWithText("حساب کاربری ندارید؟ ثبت‌نام").performClick()
        composeTestRule.onNodeWithText("ایجاد حساب کاربری").assertIsDisplayed()
    }



    @Test
    fun testLoginProcess() {
        // Enter email
        composeTestRule.onNodeWithText("پست الکترونیک").performTextInput("fat@gmail.com")

        // Click on login button
        composeTestRule.onNodeWithText("ورود").performClick()

        // Check for the presence of an element that should be on the home screen
        composeTestRule.onNodeWithText("بستن").assertIsDisplayed()
    }
}