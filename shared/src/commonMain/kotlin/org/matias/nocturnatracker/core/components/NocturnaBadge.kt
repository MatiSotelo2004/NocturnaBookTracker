package org.matias.nocturnatracker.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.theme.NocturnaCrimson
import org.matias.nocturnatracker.core.theme.NocturnaGold
import org.matias.nocturnatracker.core.theme.NocturnaTextPrimary

enum class BadgeStyle {
    Gold,
    Crimson,
    Muted
}

@Composable
fun NocturnaBadge(
    text: String,
    modifier: Modifier = Modifier,
    style: BadgeStyle = BadgeStyle.Gold
) {
    val (bgColor, textColor, borderColor) = when (style) {
        BadgeStyle.Gold -> Triple(
            NocturnaGold.copy(alpha = 0.15f),
            NocturnaGold,
            NocturnaGold.copy(alpha = 0.4f)
        )
        BadgeStyle.Crimson -> Triple(
            NocturnaCrimson.copy(alpha = 0.2f),
            Color(0xFFFF8B9C),
            NocturnaCrimson.copy(alpha = 0.5f)
        )
        BadgeStyle.Muted -> Triple(
            Color.White.copy(alpha = 0.05f),
            NocturnaTextPrimary.copy(alpha = 0.8f),
            Color.White.copy(alpha = 0.1f)
        )
    }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(16.dp))
            .border(0.8.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.3.sp
        )
    }
}
