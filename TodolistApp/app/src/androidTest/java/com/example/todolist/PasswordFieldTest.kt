package com.example.todolist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PasswordFieldTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testPasswordField_ShowHideIcon() {
        val passwordTextField = composeTestRule.onNodeWithText("رمز عبور")
        val showIcon = composeTestRule.onNodeWithContentDescription("Show password")
        val hideIcon = composeTestRule.onNodeWithContentDescription("Hide password")

        // Initially, the password should be hidden
        showIcon.assertIsDisplayed()
        hideIcon.assertDoesNotExist()

        // Enter password
        passwordTextField.performTextInput("password")

        // Click on show icon to make password visible
        showIcon.performClick()

        // Now, the hide icon should be displayed
        hideIcon.assertIsDisplayed()
        showIcon.assertDoesNotExist()

        // Click on hide icon to make password hidden again
        hideIcon.performClick()

        // The show icon should be displayed again
        showIcon.assertIsDisplayed()
        hideIcon.assertDoesNotExist()
    }
}