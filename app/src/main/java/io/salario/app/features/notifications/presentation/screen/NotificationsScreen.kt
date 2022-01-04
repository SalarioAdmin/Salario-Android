package io.salario.app.features.notifications.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.FEATURES_GRAPH_ROUTE

@ExperimentalComposeUiApi
@Composable
fun NotificationsScreen(navController: NavController) {
    BackHandler(enabled = true) {
        navController.navigate(FEATURES_GRAPH_ROUTE) {
            popUpTo(Destination.NotificationsDestination.route) {
                inclusive = true
            }
        }
    }

    NotificationsContent(
        isLoading = false
    )
}

@ExperimentalComposeUiApi
@Composable
fun NotificationsContent(
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

        Text(text = "Notifications")
    }
}