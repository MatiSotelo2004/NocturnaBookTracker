package org.matias.nocturnatracker.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.matias.nocturnatracker.core.components.NocturnaButton
import org.matias.nocturnatracker.core.components.NocturnaTextField

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (state.isSignUpMode) "Crear Cuenta" else "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        NocturnaTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChanged,
            label = "Correo Electrónico",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        NocturnaTextField(
            value = state.pass,
            onValueChange = viewModel::onPassChanged,
            label = "Contraseña",
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        state.error?.let { err ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = err, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        } else {
            NocturnaButton(
                text = if (state.isSignUpMode) "Registrarse" else "Ingresar",
                onClick = viewModel::submit,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = viewModel::toggleMode) {
            Text(
                text = if (state.isSignUpMode) "¿Ya tenés cuenta? Inicia Sesión" else "¿No tenés cuenta? Regístrate",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}