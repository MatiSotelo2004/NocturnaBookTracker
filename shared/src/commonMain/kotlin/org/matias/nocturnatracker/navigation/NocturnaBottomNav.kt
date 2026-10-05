package org.matias.nocturnatracker.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.theme.*

sealed class NavItem(
    val route: String,
    val title: String,
    val iconText: String
) {
    data object Search : NavItem(Screen.Search.route, "Explorar", "✦")
    data object Library : NavItem(Screen.Library.route, "Mi Grimorio", "📖")
    data object Profile : NavItem(Screen.Profile.route, "Perfil", "⚜")
}

@Composable
fun NocturnaBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(NavItem.Search, NavItem.Library, NavItem.Profile)

    NavigationBar(
        containerColor = NocturnaSurface,
        contentColor = NocturnaGold,
        modifier = modifier
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigate(item.route)
                    }
                },
                icon = {
                    Text(
                        text = item.iconText,
                        fontSize = 18.sp,
                        color = if (isSelected) NocturnaGold else NocturnaTextMuted
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) NocturnaGold else NocturnaTextMuted
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NocturnaGold,
                    selectedTextColor = NocturnaGold,
                    indicatorColor = NocturnaGold.copy(alpha = 0.15f),
                    unselectedIconColor = NocturnaTextMuted,
                    unselectedTextColor = NocturnaTextMuted
                )
            )
        }
    }
}
