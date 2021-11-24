package io.salario.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.salario.app.presentation.screens.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.SplashDestination.route) {
        composable(route = Destination.SplashDestination.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Destination.IntroDestination.route) {
            IntroScreen(navController = navController)
        }

        composable(route = Destination.SignInDestination.route) {
            SignInScreen(navController = navController)
        }

        composable(route = Destination.SignUpDestination.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Destination.StatusDestination.route) {
            StatusScreen(navController = navController)
        }
    }
}