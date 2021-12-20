package io.salario.app.features.auth.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.AuthenticateUser
import io.salario.app.features.auth.domain.use_case.ResetPasswordRequest
import io.salario.app.features.auth.presentation.state.SignInState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticateUser: AuthenticateUser,
    private val resetPassword: ResetPasswordRequest
) : ViewModel() {
    var signInState by mutableStateOf(SignInState())
        private set

    fun onSignIn(email: String, password: String) {
        authenticateUser(email, password)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        signInState = signInState.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        signInState = signInState.copy(
                            errorMessage = null,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        signInState = signInState.copy(
                            errorMessage = null,
                            isLoading = false,
                            shouldNavigateForward = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onResetPassword(email: String) {
        resetPassword(email)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        signInState = signInState.copy(
                            errorMessage = "Error occurred",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        signInState = signInState.copy(
                            errorMessage = null,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        // TODO what to do when success
                        signInState = signInState.copy(
                            errorMessage = null,
                            isLoading = false,
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("yoss", "Sign in VM cleared!")
    }
}