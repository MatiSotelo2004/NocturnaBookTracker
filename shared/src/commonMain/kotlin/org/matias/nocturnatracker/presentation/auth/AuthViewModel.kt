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
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSignUpMode: Boolean = false
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val currentUser = authRepository.currentUser

    fun onEmailChanged(email: String) { _uiState.value = _uiState.value.copy(email = email) }
    fun onPassChanged(pass: String) { _uiState.value = _uiState.value.copy(pass = pass) }
    fun toggleMode() { _uiState.value = _uiState.value.copy(isSignUpMode = !_uiState.value.isSignUpMode, error = null) }

    fun submit() {
        val state = _uiState.value
        if (state.email.isBlank() || state.pass.isBlank()) {
            _uiState.value = state.copy(error = "Por favor completa todos los campos.")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            val result = if (state.isSignUpMode) {
                authRepository.signUp(state.email, state.pass)
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