package org.matias.nocturnatracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.matias.nocturnatracker.core.components.NocturnaTopBar
import org.matias.nocturnatracker.core.theme.NocturnaPrimary
import org.matias.nocturnatracker.core.theme.NocturnaTheme
import org.matias.nocturnatracker.data.repository.AuthRepositoryImpl
import org.matias.nocturnatracker.navigation.NocturnaBottomBar
import org.matias.nocturnatracker.navigation.Screen
import org.matias.nocturnatracker.presentation.about.AboutScreen
import org.matias.nocturnatracker.presentation.auth.AuthViewModel
import org.matias.nocturnatracker.presentation.detail.DetailScreen
import org.matias.nocturnatracker.presentation.library.LibraryScreen
import org.matias.nocturnatracker.presentation.profile.ProfileScreen
import org.matias.nocturnatracker.presentation.search.SearchScreen

@Composable
fun App() {
    val authRepository = remember { AuthRepositoryImpl() }
    val authViewModel = remember { AuthViewModel(authRepository) }

    NocturnaTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val isDetailScreen = currentRoute?.startsWith("detail") == true

        Scaffold(
            containerColor = NocturnaPrimary,
            topBar = {
                NocturnaTopBar(
                    title = when {
                        currentRoute == Screen.Library.route -> "MI GRIMORIO"
                        currentRoute == Screen.Profile.route -> "PERFIL"
                        currentRoute == Screen.About.route -> "ACERCA DE NOCTURNA"
                        isDetailScreen -> "DETALLES"
                        else -> "NOCTURNA"
                    }
                )
            },
            bottomBar = {
                if (!isDetailScreen && currentRoute != Screen.About.route) {
                    NocturnaBottomBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.Search.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Search.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(Screen.Search.route) {
                    SearchScreen(
                        onBookClick = { bookId ->
                            navController.navigate(Screen.Detail.createRoute(bookId))
                        }
                    )
                }

                composable(Screen.Library.route) {
                    LibraryScreen(
                        onNavigateToSearch = {
                            navController.navigate(Screen.Search.route) {
                                popUpTo(Screen.Search.route) { inclusive = true }
                            }
                        },
                        onBookClick = { bookId ->
                            navController.navigate(Screen.Detail.createRoute(bookId))
                        }
                    )
                }

                composable(Screen.Profile.route) {
                    ProfileScreen(
                        authViewModel = authViewModel,
                        onNavigateToAbout = {
                            navController.navigate(Screen.About.route)
                        }
                    )
                }

                composable(Screen.About.route) {
                    AboutScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(
                        navArgument("bookId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                    DetailScreen(
                        bookId = bookId,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
