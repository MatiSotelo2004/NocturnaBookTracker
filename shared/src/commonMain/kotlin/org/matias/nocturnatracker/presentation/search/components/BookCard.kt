package org.matias.nocturnatracker.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.matias.nocturnatracker.core.components.BadgeStyle
import org.matias.nocturnatracker.core.components.NocturnaBadge
import org.matias.nocturnatracker.core.components.NocturnaCard
import org.matias.nocturnatracker.core.theme.*
import org.matias.nocturnatracker.domain.model.Book

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NocturnaCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            // Book Cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
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
                        text = "📖\nNocturna",
                        color = NocturnaGold,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )
                }

                // Optional Rating Tag in top corner
                if (book.rating != null && book.rating > 0.0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .background(NocturnaPrimary.copy(alpha = 0.85f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "★ ${book.rating}",
                            color = NocturnaGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Book Title
            Text(
                text = book.title,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = NocturnaTextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Author Name
            Text(
                text = book.author,
                fontSize = 12.sp,
                color = NocturnaTextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Genre or Year tag
            if (book.genres.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                NocturnaBadge(
                    text = book.genres.first(),
                    style = BadgeStyle.Crimson
                )
            } else if (book.firstPublishYear != null) {
                Spacer(modifier = Modifier.height(8.dp))
                NocturnaBadge(
                    text = "${book.firstPublishYear}",
                    style = BadgeStyle.Muted
                )
            }
        }
    }
}
