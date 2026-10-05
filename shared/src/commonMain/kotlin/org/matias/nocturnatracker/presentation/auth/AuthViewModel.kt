package org.matias.nocturnatracker.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.matias.nocturnatracker.domain.repository.AuthRepository

data class AuthUiState(
    val email: String = "",
    val pass: String = "",
    val confirmPass: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSignUpMode: Boolean = false,
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val currentUser = authRepository.currentUser

    fun onEmailChanged(email: String) { _uiState.value = _uiState.value.copy(email = email) }
    fun onPassChanged(pass: String) { _uiState.value = _uiState.value.copy(pass = pass) }
    fun onConfirmPassChanged(confirmPass: String) { _uiState.value = _uiState.value.copy(confirmPass = confirmPass) }
    fun onUsernameChanged(username: String) { _uiState.value = _uiState.value.copy(username = username) }
    fun togglePasswordVisibility() { _uiState.value = _uiState.value.copy(passwordVisible = !_uiState.value.passwordVisible) }
    fun toggleConfirmPasswordVisibility() { _uiState.value = _uiState.value.copy(confirmPasswordVisible = !_uiState.value.confirmPasswordVisible) }

    fun toggleMode() {
        _uiState.value = _uiState.value.copy(
            isSignUpMode = !_uiState.value.isSignUpMode,
            error = null,
            confirmPass = "",
            username = ""
        )
    }

    fun submit() {
        val state = _uiState.value
        if (state.email.isBlank() || state.pass.isBlank()) {
            _uiState.value = state.copy(error = "Por favor completa todos los campos.")
            return
        }

        if (state.isSignUpMode) {
            if (state.username.isBlank()) {
                _uiState.value = state.copy(error = "Por favor ingresa un nombre de usuario.")
                return
            }
            if (state.pass != state.confirmPass) {
                _uiState.value = state.copy(error = "Las contraseñas no coinciden.")
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            val result = if (state.isSignUpMode) {
                authRepository.signUp(state.email, state.pass, state.username)
            } else {
                authRepository.signIn(state.email, state.pass)
            }

            result.onFailure {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message ?: "Ocurrió un error")
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch { authRepository.signOut() }
    }
}
