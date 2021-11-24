package io.salario.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.salario.app.presentation.screens.IntroScreen
import io.salario.app.presentation.screens.SignInScreen
import io.salario.app.presentation.screens.SignUpScreen
import io.salario.app.presentation.screens.StatusScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.IntroDestination.route) {
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