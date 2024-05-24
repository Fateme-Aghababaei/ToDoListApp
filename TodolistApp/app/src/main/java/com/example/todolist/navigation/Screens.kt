package com.example.todolist.navigation;

/**
 * Represents the screens used in the navigation of the to-do list application.
 */
sealed class Screens(val route: String) {
    /**
     * Represents the login screen.
     */
    data object ScreenLogin : Screens("login")

    /**
     * Represents the signup screen.
     */
    data object ScreenSignup : Screens("signup")

    /**
     * Represents the home screen.
     */
    data object ScreenHome : Screens("home")

    /**
     * Represents the add task screen.
     * @property createRoute A function to create a route with an initial title.
     */
    data object ScreenAdd : Screens("add?initialTitle={initialTitle}") {
        /**
         * Creates a route with the given initial title.
         * @param initialTitle The initial title for the task.
         * @return The route with the initial title.
         */
        fun createRoute(initialTitle: String) = "add?initialTitle=$initialTitle"
    }

    /**
     * Represents the profile screen.
     */
    data object ScreenProfile : Screens("profile")

    /**
     * Represents the authentication route.
     */
    data object AuthenticationRoute : Screens("auth")

    /**
     * Represents the application route.
     */
    data object AppRoute : Screens("app")
}
