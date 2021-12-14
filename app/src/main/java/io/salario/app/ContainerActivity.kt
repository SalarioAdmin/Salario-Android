package io.salario.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import io.salario.app.core.navigation.Navigation
import io.salario.app.core.theme.SalarioTheme
import io.salario.app.core.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ContainerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SalarioTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    authViewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is AuthViewModel.UIEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }
                Scaffold(
                    Modifier.background(MaterialTheme.colors.background),
                    scaffoldState = scaffoldState
                ) {
                    Navigation(authViewModel)
                }
            }
        }
    }
}