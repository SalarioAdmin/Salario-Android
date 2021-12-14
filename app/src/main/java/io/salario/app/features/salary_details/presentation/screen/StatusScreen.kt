package io.salario.app.features.salary_details.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.salario.app.core.navigation.Destination
import io.salario.app.core.presentation.viewmodel.AuthViewModel

@Composable
fun StatusScreen(navController: NavController, authViewModel: AuthViewModel) {
    val state = authViewModel.userAuthState

    LaunchedEffect(key1 = true) {
        if (state.shouldLogout) {
            navController.navigate(Destination.IntroDestination.route) {
                popUpTo(Destination.StatusDestination.route) {
                    inclusive = true
                }
            }
        }
    }

    StatusScreenContent(
        isLoading = state.isLoading,
        name = "${state.userData?.firstName} ${state.userData?.lastName}"
    ) {
        authViewModel.onLogout()
    }
}

@Composable
fun StatusScreenContent(isLoading: Boolean, name: String, onLogoutPressed: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Text("Welcome $name!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLogoutPressed
        ) {
            Text(text = "Logout")
        }
    }
}