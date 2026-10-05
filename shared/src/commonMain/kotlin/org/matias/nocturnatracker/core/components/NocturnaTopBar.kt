package org.matias.nocturnatracker.core.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.theme.NocturnaGold
import org.matias.nocturnatracker.core.theme.NocturnaPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NocturnaTopBar(
    title: String = "NOCTURNA",
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    onSignOutClick: (() -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = 2.sp,
                color = NocturnaGold
            )
        },
        navigationIcon = {
            navigationIcon?.invoke()
        },
        actions = {
            if (onSignOutClick != null) {
                TextButton(onClick = onSignOutClick) {
                    Text(
                        text = "SALIR",
                        color = NocturnaGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            actions?.invoke(this)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NocturnaPrimary
        ),
        modifier = modifier
    )
}
