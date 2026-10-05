package org.matias.nocturnatracker.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.matias.nocturnatracker.core.components.*
import org.matias.nocturnatracker.core.theme.*

@Composable
fun DetailScreen(
    bookId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel { DetailViewModel(bookId) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val book = uiState.book

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NocturnaPrimary)
    ) {
        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = NocturnaGold,
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Desentrañando las páginas...",
                        fontSize = 13.sp,
                        color = NocturnaTextSecondary
                    )
                }
            }

            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Grimorio inaccesible",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = NocturnaCrimson
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.errorMessage ?: "Error desconocido",
                        fontSize = 13.sp,
                        color = NocturnaTextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    NocturnaButton(
                        text = "Reintentar",
                        onClick = { viewModel.loadBook() },
                        variant = NocturnaButtonVariant.OutlinedGold
                    )
                }
            }

            book != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Book Cover Card
                    NocturnaCard(
                        modifier = Modifier
                            .width(180.dp)
                            .height(260.dp),
                        highlightGold = true
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(NocturnaSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!book.coverUrl.isNullOrBlank()) {
                                AsyncImage(
                                    model = book.coverUrl,
                                    contentDescription = book.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text(
                                    text = "📖\n${book.title}",
                                    textAlign = TextAlign.Center,
                                    color = NocturnaGold,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Title
                    Text(
                        text = book.title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = NocturnaTextPrimary,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Author
                    Text(
                        text = book.author,
                        fontSize = 15.sp,
                        color = NocturnaGold,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Badges row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (book.rating != null && book.rating > 0.0) {
                            NocturnaBadge(
                                text = "★ ${book.rating} / 5",
                                style = BadgeStyle.Gold
                            )
                        }
                        book.genres.take(2).forEach { genre ->
                            NocturnaBadge(text = genre, style = BadgeStyle.Crimson)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Reading Tracker Card
                    NocturnaCard(
                        modifier = Modifier.fillMaxWidth(),
                        highlightGold = uiState.isSaved
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Estado de Lectura",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = NocturnaGold
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("Por Leer", "Leyendo", "Completado").forEach { status ->
                                    val isSelected = uiState.readingStatus == status && uiState.isSaved
                                    NocturnaCard(
                                        modifier = Modifier.weight(1f),
                                        highlightGold = isSelected,
                                        onClick = { viewModel.setReadingStatus(status) }
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = status,
                                                fontSize = 11.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) NocturnaGold else NocturnaTextSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description Container
                    NocturnaCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Sinopsis de la Noche",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = NocturnaGold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = book.description ?: "Una misteriosa obra que aguarda ser descubierta bajo la luz de la luna.",
                                fontSize = 14.sp,
                                color = NocturnaTextSecondary,
                                lineHeight = 22.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action buttons
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
                            text = if (uiState.isSaved) "En tu Grimorio ✓" else "Guardar en Grimorio",
                            onClick = { viewModel.toggleSaved() },
                            variant = if (uiState.isSaved) NocturnaButtonVariant.Crimson else NocturnaButtonVariant.Gold,
                            modifier = Modifier.weight(1.6f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
