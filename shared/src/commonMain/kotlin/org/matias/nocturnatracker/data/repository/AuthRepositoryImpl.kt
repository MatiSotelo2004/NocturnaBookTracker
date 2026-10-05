package org.matias.nocturnatracker.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.matias.nocturnatracker.data.local.PlatformLocalStorage
import org.matias.nocturnatracker.domain.repository.AuthRepository
import org.matias.nocturnatracker.domain.repository.User

class AuthRepositoryImpl(
    private val localStorage: PlatformLocalStorage = PlatformLocalStorage()
) : AuthRepository {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore
    private val keyUsername = "logged_in_username"

    override val currentUser: Flow<User?> = auth.authStateChanged.map { firebaseUser ->
        if (firebaseUser == null) {
            localStorage.remove(keyUsername)
            null
        } else {
            val uid = firebaseUser.uid
            val email = firebaseUser.email
            var username = localStorage.getString(keyUsername)
            if (username.isNullOrBlank()) {
                username = email?.substringBefore("@")?.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } ?: "Lector Nocturno"
            }
            User(uid = uid, email = email, username = username)
        }
    }

    override suspend fun signIn(email: String, pass: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, pass)
        val uid = auth.currentUser?.uid
        if (uid != null) {
            try {
                val doc = firestore.collection("users").document(uid).get()
                val uname = doc.get<String>("username")
                if (!uname.isNullOrBlank()) {
                    localStorage.putString(keyUsername, uname)
                } else {
                    val fallback = email.substringBefore("@").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                    localStorage.putString(keyUsername, fallback)
                }
            } catch (_: Exception) {
                val fallback = email.substringBefore("@").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                localStorage.putString(keyUsername, fallback)
            }
        }
    }

    override suspend fun signUp(email: String, pass: String, username: String): Result<Unit> = runCatching {
        auth.createUserWithEmailAndPassword(email, pass)
        val uid = auth.currentUser?.uid ?: error("User not found after signup")
        
        firestore.collection("users").document(uid).set(
            mapOf(
                "email" to email,
                "username" to username
            )
        )
        localStorage.putString(keyUsername, username)
    }

    override suspend fun signOut() {
        localStorage.remove(keyUsername)
        auth.signOut()
    }
}
