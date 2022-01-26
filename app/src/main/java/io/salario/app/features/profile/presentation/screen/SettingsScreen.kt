package io.salario.app.features.profile.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.salario.app.core.domain.model.UIEvent
import io.salario.app.core.navigation.Destination
import io.salario.app.core.shared_ui.composable.InfoDialog
import io.salario.app.core.shared_ui.composable.LoadingDialog
import io.salario.app.features.profile.presentation.event.SettingsEvent
import io.salario.app.features.profile.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(event.route) {
                        popUpTo(Destination.SettingsDestination.route) {
                            inclusive = true
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    BackHandler(enabled = true) {
        viewModel.onEvent(SettingsEvent.OnBackPressed)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewModel.loadingDialogConfig.isActive) {
            LoadingDialog(viewModel.loadingDialogConfig.loadingType)
        }

        if (viewModel.infoDialogConfig.isActive) {
            InfoDialog(
                infoType = viewModel.infoDialogConfig.infoType,
                title = viewModel.infoDialogConfig.title,
                subtitle = viewModel.infoDialogConfig.subtitle,
                onDismissPressed = {
                    viewModel.onEvent(SettingsEvent.OnDialogDismiss)
                }
            )
        }

        Button(
            onClick = { viewModel.onEvent(SettingsEvent.OnLogoutPressed) }
        ) {
            Text(text = "Logout")
        }
    }
}