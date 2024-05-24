package com.example.todolist.viewModel

import com.example.todolist.models.User

/**
 * Interface defining methods for user-related operations.
 */
interface UserRepository {
    /**
     * Logs in the user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A Pair consisting of the authentication token and an error message (if any).
     */
    suspend fun login(email: String, password: String): Pair<String, String>

    /**
     * Signs up a new user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A Pair consisting of the authentication token and an error message (if any).
     */
    suspend fun signup(email: String, password: String): Pair<String, String>

    /**
     * Logs out the user identified by the given token.
     * @param token The authentication token of the user to be logged out.
     * @return true if the logout operation was successful, false otherwise.
     */
    suspend fun logout(token: String): Boolean

    /**
     * Retrieves user information for the user identified by the given token.
     * @param token The authentication token of the user.
     * @return The User object representing the user, or null if no user is found.
     */
    suspend fun getUser(token: String): User?
}
