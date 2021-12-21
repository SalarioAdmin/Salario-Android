package io.salario.app.features.salary_analytics.presentation.screen

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
fun SalaryAnalyticsScreen(navController: NavController) {
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog = true
    }

    SalaryAnalyticsContent(
        isLoading = false,
        showExitDialog = showExitDialog,
        onDismissPressed = {
            showExitDialog = false
        },
        onExitPressed = {
            navController.popBackStack()
        }
    )
}

@ExperimentalComposeUiApi
@Composable
fun SalaryAnalyticsContent(
    isLoading: Boolean,
    showExitDialog: Boolean,
    onExitPressed: () -> Unit,
    onDismissPressed: () -> Unit
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

        Text(text = "Analytics")
    }
}