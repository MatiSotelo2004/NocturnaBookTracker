package org.matias.nocturnatracker.presentation.auth

import kotlinx.coroutines.flow.flowOf
import org.matias.nocturnatracker.domain.repository.AuthRepository
import org.matias.nocturnatracker.domain.repository.User
import kotlin.test.Test
import kotlin.test.assertEquals

class FakeAuthRepository : AuthRepository {
    override val currentUser = flowOf<User?>(null)
    var lastEmail = ""
    var lastPass = ""
    var lastUsername = ""

    override suspend fun signIn(email: String, pass: String): Result<Unit> {
        lastEmail = email
        lastPass = pass
        return Result.success(Unit)
    }

    override suspend fun signUp(email: String, pass: String, username: String): Result<Unit> {
        lastEmail = email
        lastPass = pass
        lastUsername = username
        return Result.success(Unit)
    }

    override suspend fun signOut() {}
}

class AuthViewModelTest {

    @Test
    fun testEmptyFieldsError() {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.submit()

        val state = viewModel.uiState.value
        assertEquals("Por favor completa todos los campos.", state.error)
    }

    @Test
    fun testSignUpPasswordMismatchError() {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.toggleMode() // switch to sign up
        viewModel.onUsernameChanged("Matias")
        viewModel.onEmailChanged("test@nocturna.com")
        viewModel.onPassChanged("password123")
        viewModel.onConfirmPassChanged("different123")

        viewModel.submit()

        val state = viewModel.uiState.value
        assertEquals("Las contraseñas no coinciden.", state.error)
    }
}
