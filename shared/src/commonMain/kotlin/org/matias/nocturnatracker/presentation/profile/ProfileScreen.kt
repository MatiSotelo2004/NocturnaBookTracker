package org.matias.nocturnatracker.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.components.NocturnaButton
import org.matias.nocturnatracker.core.components.NocturnaButtonVariant
import org.matias.nocturnatracker.core.components.NocturnaCard
import org.matias.nocturnatracker.core.theme.*
import org.matias.nocturnatracker.presentation.auth.AuthScreen
import org.matias.nocturnatracker.presentation.auth.AuthViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onNavigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser by authViewModel.currentUser.collectAsState(initial = null)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NocturnaPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentUser != null) {
                val user = currentUser!!
                val email = user.email ?: "Sin Correo"
                val name = user.username?.takeIf { it.isNotBlank() }
                    ?: email.substringBefore("@").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

                Spacer(modifier = Modifier.height(12.dp))

                // Avatar and User Info Card
                NocturnaCard(
                    modifier = Modifier.fillMaxWidth(),
                    highlightGold = true
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(NocturnaSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "⚜",
                                fontSize = 32.sp,
                                color = NocturnaGold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // User Name
                        Text(
                            text = name,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = NocturnaTextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Email
                        Text(
                            text = email,
                            fontSize = 14.sp,
                            color = NocturnaTextSecondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Cloud status badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(NocturnaGold.copy(alpha = 0.15f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "☁ Sincronizado en la nube",
                                fontSize = 12.sp,
                                color = NocturnaGold,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sign Out Button
                NocturnaButton(
                    text = "Cerrar Sesión",
                    onClick = { authViewModel.signOut() },
                    variant = NocturnaButtonVariant.Crimson,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // About Nocturna Button
                NocturnaButton(
                    text = "Acerca de Nocturna",
                    onClick = onNavigateToAbout,
                    variant = NocturnaButtonVariant.OutlinedGold,
                    modifier = Modifier.fillMaxWidth()
                )

            } else {
                // Guest mode
                Spacer(modifier = Modifier.height(8.dp))

                NocturnaCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Navegando como Invitado",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = NocturnaGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Tus libros se están guardando localmente en tu dispositivo. Inicia sesión para sincronizarlos con la nube.",
                            fontSize = 13.sp,
                            color = NocturnaTextSecondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Login / Registration Form
                AuthScreen(viewModel = authViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                // About Nocturna Button
                NocturnaButton(
                    text = "Acerca de Nocturna",
                    onClick = onNavigateToAbout,
                    variant = NocturnaButtonVariant.OutlinedGold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
