package io.salario.app.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import io.salario.app.presentation.navigation.Navigation
import io.salario.app.presentation.theme.SalarioTheme
import io.salario.app.presentation.viewmodels.AuthenticationViewModel

class ContainerActivity : ComponentActivity() {
    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SalarioTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(authViewModel)
                }
            }
        }
    }
}