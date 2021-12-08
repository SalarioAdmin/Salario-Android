package io.salario.app.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import io.salario.app.data.utils.Status
import io.salario.app.presentation.navigation.Destination
import io.salario.app.presentation.viewmodels.AuthenticationViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun StatusScreen(navController: NavController, authViewModel: AuthenticationViewModel) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        authViewModel.logoutResult.collect {
            when (it.status) {
                is Status.Success -> {
                    navController.navigate(Destination.SignInDestination.route) {
                        popUpTo(Destination.StatusDestination.route) {
                            inclusive = true
                        }
                    }
                }
                is Status.Loading -> {
                    Toast
                        .makeText(context, "Loading...", Toast.LENGTH_SHORT)
                        .show()
                }
                is Status.Error -> {
                    Toast
                        .makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Text("Welcome ${authViewModel.getConnectedUser()!!.firstName}!")
        Text("Welcome!")
        Button(
            onClick = { authViewModel.logout() }
        ) {
            Text(text = "Logout")
        }
    }
}