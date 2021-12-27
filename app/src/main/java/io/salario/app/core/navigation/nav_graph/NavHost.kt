package io.salario.app.core.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.salario.app.core.navigation.Destination
import io.salario.app.features.splash_screen.presentation.screen.SplashScreen

@ExperimentalComposeUiApi
@Composable
fun Navigation(
    navController: NavHostController,
    onBottomNavVisibilityChange: (show: Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Destination.SplashDestination.route
    ) {
        composable(route = Destination.SplashDestination.route) {
            SplashScreen(navController = navController)
        }

        authNavGraph(
            navController = navController,
            applyVisibilityConfig = {
                onBottomNavVisibilityChange(false)
            }
        )

        featuresNavigationGraph(
            navController = navController,
            applyVisibilityConfig = {
                onBottomNavVisibilityChange(true)
            }
        )
    }
}