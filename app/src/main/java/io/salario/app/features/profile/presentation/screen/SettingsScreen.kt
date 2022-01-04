package io.salario.app.features.profile.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.salario.app.core.navigation.AUTH_GRAPH_ROUTE
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.FEATURES_GRAPH_ROUTE
import io.salario.app.features.profile.presentation.viewmodel.SettingsViewModel

@ExperimentalComposeUiApi
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    BackHandler(enabled = true) {
        navController.navigate(FEATURES_GRAPH_ROUTE) {
            popUpTo(Destination.SettingsDestination.route) {
                inclusive = true
            }
        }
    }

    viewModel.authState.apply {
        if (shouldLogout) {
            LaunchedEffect(shouldLogout) {
                navController.navigate(AUTH_GRAPH_ROUTE) {
                    popUpTo(Destination.StatusDestination.route) {
                        inclusive = true
                    }
                }
            }
        }

        SettingsContent(
            isLoading = isLoading,
            onLogoutPressed = {
                viewModel.onLogout()
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun SettingsContent(
    isLoading: Boolean,
    onLogoutPressed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Button(
            onClick = onLogoutPressed
        ) {
            Text(text = "Logout")
        }
    }
}