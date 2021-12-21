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
import io.salario.app.core.customui.composable.ExitAlertDialog

@ExperimentalComposeUiApi
@Composable
fun NotificationsScreen(navController: NavController) {
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog = true
    }

    NotificationsContent(
        isLoading = false,
        showExitDialog = showExitDialog,
        onDismissPressed = {
            showExitDialog = false
        }
    ) {
        navController.popBackStack()
    }
}

@ExperimentalComposeUiApi
@Composable
fun NotificationsContent(
    isLoading: Boolean,
    showExitDialog: Boolean,
    onDismissPressed: () -> Unit,
    onExitPressed: () -> Unit
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

        Text(text = "Notifications")
    }
}