package org.matias.nocturnatracker.domain.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.matias.nocturnatracker.domain.repository.AuthRepository
import org.matias.nocturnatracker.domain.repository.User

class AuthRepositoryImpl : AuthRepository {
    private val auth = Firebase.auth

    override val currentUser: Flow<User?> = auth.authStateChanged.map { firebaseUser ->
        firebaseUser?.let { User(it.uid, it.email) }
    }

    override suspend fun signIn(email: String, pass: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, pass)
    }

    override suspend fun signUp(email: String, pass: String): Result<Unit> = runCatching {
        auth.createUserWithEmailAndPassword(email, pass)
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}