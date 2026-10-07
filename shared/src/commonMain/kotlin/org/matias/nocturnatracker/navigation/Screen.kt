package org.matias.nocturnatracker.navigation

sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object Library : Screen("library")
    data object Profile : Screen("profile")
    data object About : Screen("about")
    data object Detail : Screen("detail/{bookId}") {
        fun createRoute(bookId: String): String = "detail/$bookId"
    }
}
