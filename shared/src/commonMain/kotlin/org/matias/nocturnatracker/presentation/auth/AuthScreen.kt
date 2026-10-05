package org.matias.nocturnatracker.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.components.NocturnaButton
import org.matias.nocturnatracker.core.components.NocturnaButtonVariant
import org.matias.nocturnatracker.core.components.NocturnaCard
import org.matias.nocturnatracker.core.components.NocturnaTextField
import org.matias.nocturnatracker.core.theme.*

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    NocturnaCard(
        modifier = modifier.fillMaxWidth(),
        highlightGold = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (state.isSignUpMode) "Crear Cuenta" else "Iniciar Sesión",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = NocturnaGold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isSignUpMode) {
                NocturnaTextField(
                    value = state.username,
                    onValueChange = viewModel::onUsernameChanged,
                    label = "Nombre de Usuario",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            NocturnaTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChanged,
                label = "Correo Electrónico",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            NocturnaTextField(
                value = state.pass,
                onValueChange = viewModel::onPassChanged,
                label = "Contraseña",
                visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = viewModel::togglePasswordVisibility) {
                        Text(
                            text = if (state.passwordVisible) "👁‍🗨" else "👁",
                            fontSize = 16.sp
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (state.isSignUpMode) {
                Spacer(modifier = Modifier.height(12.dp))
                NocturnaTextField(
                    value = state.confirmPass,
                    onValueChange = viewModel::onConfirmPassChanged,
                    label = "Confirmar Contraseña",
                    visualTransformation = if (state.confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = viewModel::toggleConfirmPasswordVisibility) {
                            Text(
                                text = if (state.confirmPasswordVisible) "👁‍🗨" else "👁",
                                fontSize = 16.sp
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            state.error?.let { err ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = err,
                    color = NocturnaCrimsonLight,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (state.isLoading) {
                CircularProgressIndicator(
                    color = NocturnaGold,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                NocturnaButton(
                    text = if (state.isSignUpMode) "Registrarse" else "Ingresar",
                    onClick = viewModel::submit,
                    variant = NocturnaButtonVariant.Gold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = viewModel::toggleMode) {
                Text(
                    text = if (state.isSignUpMode) "¿Ya tenés cuenta? Inicia Sesión" else "¿No tenés cuenta? Regístrate",
                    color = NocturnaGold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
