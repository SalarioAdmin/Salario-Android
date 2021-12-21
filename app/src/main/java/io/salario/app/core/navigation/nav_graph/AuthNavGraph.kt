package io.salario.app.core.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.salario.app.core.navigation.AUTH_GRAPH_ROUTE
import io.salario.app.core.navigation.Destination
import io.salario.app.features.auth.presentation.IntroScreen
import io.salario.app.features.auth.presentation.screen.EmailValidationScreen
import io.salario.app.features.auth.presentation.screen.SignInScreen
import io.salario.app.features.auth.presentation.screen.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    applyVisibilityConfig: (() -> Unit)? = null
) {
    navigation(
        startDestination = Destination.IntroDestination.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(route = Destination.IntroDestination.route) {
            applyVisibilityConfig?.invoke()
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
    }
}