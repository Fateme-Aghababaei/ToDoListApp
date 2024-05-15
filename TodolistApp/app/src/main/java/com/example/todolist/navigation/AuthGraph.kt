package com.example.todolist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.todolist.screens.LoginScreen
import com.example.todolist.screens.SignupScreen

//fun NavGraphBuilder.authGraph(navController: NavController) {
//    navigation(
//        startDestination = Screens.ScreenLogin.route,
//        route = Screens.AuthenticationRoute.route
//    ) {
//        composable(route = Screens.ScreenSignup.route) {
//            SignupScreen(navController = navController)
//        }
//        composable(route = Screens.ScreenLogin.route) {
//            LoginScreen(navController = navController)
//        }
//    }
//}