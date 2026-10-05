package org.matias.nocturnatracker.presentation.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.matias.nocturnatracker.core.components.NocturnaButton
import org.matias.nocturnatracker.core.components.NocturnaButtonVariant
import org.matias.nocturnatracker.core.theme.*
import org.matias.nocturnatracker.presentation.search.components.BookCard

@Composable
fun LibraryScreen(
    onNavigateToSearch: () -> Unit,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = viewModel { LibraryViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Leyendo", "Por Leer", "Completados")

    val selectedStatus = when (selectedTab) {
        0 -> "Leyendo"
        1 -> "Por Leer"
        else -> "Completado"
    }

    val filteredBooks = uiState.books.filter { book ->
        if (selectedStatus == "Completado") {
            book.status == "Completado" || book.status == "Completados"
        } else {
            book.status == selectedStatus
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NocturnaPrimary)
    ) {
        // Tab Row
        SecondaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = NocturnaSurface,
            contentColor = NocturnaGold,
            divider = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(NocturnaBorder)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTab == index
                Tab(
                    selected = isSelected,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) NocturnaGold else NocturnaTextSecondary
                        )
                    }
                )
            }
        }

        if (filteredBooks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tu Grimorio está Vacío",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = NocturnaTextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Aún no has registrado libros en la categoría '${tabs[selectedTab]}'. Comienza buscando tus obras favoritas.",
                        fontSize = 14.sp,
                        color = NocturnaTextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    NocturnaButton(
                        text = "Explorar Catálogo",
                        onClick = onNavigateToSearch,
                        variant = NocturnaButtonVariant.Gold
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredBooks, key = { it.id }) { book ->
                    BookCard(
                        book = book,
                        onClick = { onBookClick(book.id) }
                    )
                }
            }
        }
    }
}
