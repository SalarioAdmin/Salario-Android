package io.salario.app.features.profile.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.salario.app.core.navigation.AUTH_GRAPH_ROUTE
import io.salario.app.core.navigation.Destination
import io.salario.app.core.shared_ui.composable.ExitAlertDialog
import io.salario.app.features.profile.presentation.viewmodel.SettingsViewModel

@ExperimentalComposeUiApi
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
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

        var showExitDialog by remember { mutableStateOf(false) }

        BackHandler(enabled = true) {
            showExitDialog = true
        }

        SettingsContent(
            isLoading = isLoading,
            showExitDialog = showExitDialog,
            onDismissPressed = {
                showExitDialog = false
            },
            onExitPressed = {
                navController.popBackStack()
            },
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
    showExitDialog: Boolean,
    onExitPressed: () -> Unit,
    onDismissPressed: () -> Unit,
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

        if (showExitDialog) {
            ExitAlertDialog(onExitPressed, onDismissPressed)
        }

        Button(
            onClick = onLogoutPressed
        ) {
            Text(text = "Logout")
        }
    }
}