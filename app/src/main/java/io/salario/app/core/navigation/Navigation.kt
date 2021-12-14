package io.salario.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.salario.app.features.auth.presentation.screen.EmailValidationScreen
import io.salario.app.features.auth.presentation.screen.SignInScreen
import io.salario.app.features.auth.presentation.screen.SignUpScreen
import io.salario.app.core.presentation.viewmodel.AuthViewModel
import io.salario.app.features.intro.presentation.screen.IntroScreen
import io.salario.app.features.intro.presentation.screen.SplashScreen
import io.salario.app.features.salary_details.presentation.screen.StatusScreen

@Composable
fun Navigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.SplashDestination.route) {
        composable(route = Destination.SplashDestination.route) {
            SplashScreen(navController = navController, authViewModel)
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

        composable(route = Destination.EmailValidationDestination.route) {
            EmailValidationScreen(navController = navController)
        }

        composable(route = Destination.StatusDestination.route) {
            StatusScreen(navController = navController, authViewModel)
        }
    }
}