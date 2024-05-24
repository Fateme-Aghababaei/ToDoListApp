
package com.example.todolist.viewModel

import com.example.todolist.models.User

/**
 * Interface defining user-related repository operations.
 */
interface UserRepository {
    /**
     * Logs in the user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A pair containing the user's authentication token and an optional error message.
     */
    suspend fun login(email: String, password: String): Pair<String, String>

    /**
     * Signs up the user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A pair containing the user's authentication token and an optional error message.
     */
    suspend fun signup(email: String, password: String): Pair<String, String>

    /**
     * Logs out the user with the specified token.
     * @param token The user's authentication token.
     * @return A boolean indicating whether the logout operation was successful.
     */
    suspend fun logout(token: String): Boolean

    /**
     * Retrieves the user information associated with the provided token.
     * @param token The user's authentication token.
     * @return The user object if found, or null if no user is associated with the token.
     */
    suspend fun getUser(token: String): User?
}
