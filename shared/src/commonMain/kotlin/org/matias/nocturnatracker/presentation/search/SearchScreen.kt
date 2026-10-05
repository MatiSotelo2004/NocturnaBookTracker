package org.matias.nocturnatracker.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
fun SearchScreen(
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val genres = listOf("Todos", "Fantasía", "Terror", "Thrillers", "Sci-Fi", "Manga")
    var selectedGenre by remember { mutableStateOf("Todos") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NocturnaPrimary)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Search Input
        NocturnaTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Buscar título, autor o grimorio...",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Genre Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            genres.forEach { genre ->
                val isSelected = genre == selectedGenre
                NocturnaCard(
                    modifier = Modifier,
                    highlightGold = isSelected,
                    onClick = { selectedGenre = genre }
                ) {
                    Box(modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)) {
                        Text(
                            text = genre,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) NocturnaGold else NocturnaTextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Initial Empty State / Prompt
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Explora las Sombras",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = NocturnaGold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Encuentra tu próxima gran historia nocturna a través del catálogo de OpenLibrary.",
                    fontSize = 14.sp,
                    color = NocturnaTextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
