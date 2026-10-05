package org.matias.nocturnatracker.domain.repository

import kotlinx.coroutines.flow.Flow

data class User(
    val uid: String,
    val email: String?
)

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun signIn(email: String, pass: String): Result<Unit>
    suspend fun signUp(email: String, pass: String): Result<Unit>
    suspend fun signOut()
}