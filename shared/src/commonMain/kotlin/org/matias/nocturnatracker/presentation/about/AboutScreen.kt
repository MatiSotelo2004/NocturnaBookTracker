package org.matias.nocturnatracker.presentation.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.components.NocturnaButton
import org.matias.nocturnatracker.core.components.NocturnaButtonVariant
import org.matias.nocturnatracker.core.components.NocturnaCard
import org.matias.nocturnatracker.core.theme.*

@Composable
fun AboutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Spacer(modifier = Modifier.height(12.dp))

            // Brand Header Icon
            Text(
                text = "⚜",
                fontSize = 42.sp,
                color = NocturnaGold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "NOCTURNA",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                letterSpacing = 3.sp,
                color = NocturnaGold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Santuario de Libros y Mangas",
                fontSize = 14.sp,
                color = NocturnaTextSecondary,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card 1: Essence
            NocturnaCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Nuestra Esencia",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = NocturnaGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nocturna nace como un santuario digital y ecommerce especializado en literatura oscura, fantasía gótica, terror, ciencia ficción y mangas. Un espacio místico donde cada obra es una puerta abierta a universos inexplorados bajo la luz de la luna.",
                        fontSize = 14.sp,
                        color = NocturnaTextPrimary,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card 2: App Purpose
            NocturnaCard(
                modifier = Modifier.fillMaxWidth(),
                highlightGold = true
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "El Grimorio Móvil",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = NocturnaGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Esta aplicación complementa nuestra experiencia de ecommerce, permitiéndote registrar y organizar tus lecturas actuales, pendientes y completadas en tu propio 'Grimorio', explorar nuestro catálogo curado y llevar el pulso de tus aventuras literarias a donde quiera que vayas.",
                        fontSize = 14.sp,
                        color = NocturnaTextPrimary,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card 3: Web Ecommerce
            NocturnaCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Plataforma Web (Ecommerce)",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = NocturnaGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Explora nuestro ecommerce principal de libros y mangas en el repositorio oficial:",
                        fontSize = 14.sp,
                        color = NocturnaTextPrimary,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "github.com/MatiSotelo2004/nocturna-web",
                        fontSize = 13.sp,
                        color = NocturnaGold,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            NocturnaButton(
                text = "Volver al Refugio",
                onClick = onBackClick,
                variant = NocturnaButtonVariant.OutlinedGold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
