package io.salario.app.features.salary_analytics.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.FEATURES_GRAPH_ROUTE

@ExperimentalComposeUiApi
@Composable
fun SalaryAnalyticsScreen(navController: NavController) {
    BackHandler(enabled = true) {
        navController.navigate(FEATURES_GRAPH_ROUTE) {
            popUpTo(Destination.AnalyticsDestination.route) {
                inclusive = true
            }
        }
    }

    SalaryAnalyticsContent(
        isLoading = false
    )
}

@ExperimentalComposeUiApi
@Composable
fun SalaryAnalyticsContent(
    isLoading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Text(text = "Analytics")
    }
}