package com.example.todolist.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SignupScreen(navController: NavController, modifier: Modifier = Modifier) {
    Text(text = "Signup", fontSize = 24.sp, fontWeight = FontWeight.Bold)
}