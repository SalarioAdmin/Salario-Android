package io.salario.app.features.salary_details.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.salario.app.core.navigation.Destination
import io.salario.app.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun StatusScreen(navController: NavController, authViewModel: AuthViewModel) {
    if (authViewModel.userAuthState.value.shouldLogout) {
        navController.navigate(Destination.IntroDestination.route) {
            popUpTo(Destination.StatusDestination.route) {
                inclusive = true
            }
        }
        authViewModel.userAuthState.value.shouldLogout = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (authViewModel.userAuthState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        authViewModel.userAuthState.value.userData?.let { user ->
            Text("Welcome ${user.firstName} ${user.lastName}!")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = authViewModel::onLogout
            ) {
                Text(text = "Logout")
            }
        } ?: run {
            Text("Error")
        }
    }
}