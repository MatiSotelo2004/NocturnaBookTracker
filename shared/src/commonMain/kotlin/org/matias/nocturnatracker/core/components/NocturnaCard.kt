package org.matias.nocturnatracker.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.matias.nocturnatracker.core.theme.NocturnaBorder
import org.matias.nocturnatracker.core.theme.NocturnaGoldBorder
import org.matias.nocturnatracker.core.theme.NocturnaSurface

@Composable
fun NocturnaCard(
    modifier: Modifier = Modifier,
    highlightGold: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val border = if (highlightGold) {
        BorderStroke(1.dp, NocturnaGoldBorder)
    } else {
        BorderStroke(1.dp, NocturnaBorder)
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = NocturnaSurface
            ),
            border = border,
            content = content
        )
    } else {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = NocturnaSurface
            ),
            border = border,
            content = content
        )
    }
}
