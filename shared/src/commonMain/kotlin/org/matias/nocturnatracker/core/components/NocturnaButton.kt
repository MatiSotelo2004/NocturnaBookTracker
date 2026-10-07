package org.matias.nocturnatracker.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.theme.NocturnaCrimson
import org.matias.nocturnatracker.core.theme.NocturnaGold

enum class NocturnaButtonVariant {
    Gold,
    OutlinedGold,
    Crimson
}

@Composable
fun NocturnaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: NocturnaButtonVariant = NocturnaButtonVariant.Gold,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val shape = RoundedCornerShape(30.dp)

    when (variant) {
        NocturnaButtonVariant.Gold -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NocturnaGold,
                    contentColor = Color.Black,
                    disabledContainerColor = NocturnaGold.copy(alpha = 0.4f),
                    disabledContentColor = Color.Black.copy(alpha = 0.5f),
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            ) {
                leadingIcon?.invoke()
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    letterSpacing = 0.5.sp,
                )
            }
        }
        NocturnaButtonVariant.OutlinedGold -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                shape = shape,
                border = BorderStroke(1.dp, if (enabled) NocturnaGold else NocturnaGold.copy(alpha = 0.3f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = NocturnaGold,
                    disabledContentColor = NocturnaGold.copy(alpha = 0.3f),
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            ) {
                leadingIcon?.invoke()
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    letterSpacing = 0.5.sp,
                )
            }
        }
        NocturnaButtonVariant.Crimson -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NocturnaCrimson,
                    contentColor = Color.White,
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            ) {
                leadingIcon?.invoke()
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    letterSpacing = 0.5.sp,
                )
            }
        }
    }
}
