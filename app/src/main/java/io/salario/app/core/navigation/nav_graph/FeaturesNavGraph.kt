package io.salario.app.core.navigation.nav_graph

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.FEATURES_GRAPH_ROUTE
import io.salario.app.features.notifications.presentation.screen.NotificationsScreen
import io.salario.app.features.salary_analytics.presentation.screen.SalaryAnalyticsScreen
import io.salario.app.features.salary_details.presentation.screen.StatusScreen
import io.salario.app.features.settings.presentation.screen.SettingsScreen

@ExperimentalComposeUiApi
fun NavGraphBuilder.featuresNavigationGraph(
    navController: NavController,
    applyVisibilityConfig: (() -> Unit)? = null
) {
    navigation(
        startDestination = Destination.StatusDestination.route,
        route = FEATURES_GRAPH_ROUTE
    ) {
        composable(route = Destination.StatusDestination.route) {
            applyVisibilityConfig?.invoke()
            StatusScreen(navController = navController)
        }

        composable(route = Destination.AnalyticsDestination.route) {
            SalaryAnalyticsScreen(navController = navController)
        }

        composable(route = Destination.NotificationsDestination.route) {
            NotificationsScreen(navController = navController)
        }

        composable(route = Destination.SettingsDestination.route) {
            SettingsScreen(navController = navController)
        }
    }
}