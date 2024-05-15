package com.example.todolist.navigation

sealed class Screens (val route: String) {
    data object ScreenLogin: Screens("login")
    data object ScreenSignup: Screens("signup")
    data object ScreenHome: Screens("home")

    data object AuthenticationRoute: Screens("auth")
    data object AppRoute: Screens("app")
}