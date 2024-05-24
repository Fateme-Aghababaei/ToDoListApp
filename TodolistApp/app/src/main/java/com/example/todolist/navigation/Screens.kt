package com.example.todolist.navigation

/**
 * Sealed class representing different screens in the application.
 *
 * @property route The route associated with the screen.
 */
sealed class Screens (val route: String) {
    data object ScreenLogin: Screens("login")
    data object ScreenSignup: Screens("signup")
    data object ScreenHome: Screens("home")
    data object ScreenAdd : Screens("add?initialTitle={initialTitle}") {
        fun createRoute(initialTitle: String) = "add?initialTitle=$initialTitle"
    }
    data object ScreenProfile: Screens("profile")
    data object AuthenticationRoute: Screens("auth")
    data object AppRoute: Screens("app")
}