package org.matias.nocturnatracker.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.components.*
import org.matias.nocturnatracker.core.theme.*

@Composable
fun DetailScreen(
    bookId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NocturnaPrimary)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Book Cover Placeholder
        NocturnaCard(
            modifier = Modifier
                .width(170.dp)
                .height(250.dp),
            highlightGold = true
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(NocturnaSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Portada\n$bookId",
                    textAlign = TextAlign.Center,
                    color = NocturnaGold,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Book Title
        Text(
            text = "Detalles de la Obra",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = NocturnaTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Author
        Text(
            text = "Identificador: $bookId",
            fontSize = 14.sp,
            color = NocturnaGold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NocturnaBadge(text = "Fantasía Oscura", style = BadgeStyle.Crimson)
            NocturnaBadge(text = "OpenLibrary", style = BadgeStyle.Gold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Description Container
        NocturnaCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Sinopsis",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = NocturnaGold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "La información detallada y sinopsis del libro se obtendrán desde la API de OpenLibrary en la siguiente fase.",
                    fontSize = 14.sp,
                    color = NocturnaTextSecondary,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NocturnaButton(
                text = "Volver",
                onClick = onBackClick,
                variant = NocturnaButtonVariant.OutlinedGold,
                modifier = Modifier.weight(1f)
            )
            NocturnaButton(
                text = "Añadir a Grimorio",
                onClick = { /* Will be handled by Library ViewModel */ },
                variant = NocturnaButtonVariant.Gold,
                modifier = Modifier.weight(1.5f)
            )
        }
    }
}
