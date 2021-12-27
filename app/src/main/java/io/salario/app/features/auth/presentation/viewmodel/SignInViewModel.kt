package io.salario.app.features.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.model.UIError
import io.salario.app.core.shared_ui.composable.DialogInfoType
import io.salario.app.core.util.ErrorType
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
                            isLoading = false
                        ).apply {
                            error = UIError(
                                result.message!!,
                                dialogType = when (result.type) {
                                    ErrorType.IO -> DialogInfoType.ErrorNoConnection
                                    ErrorType.ServerError -> DialogInfoType.ErrorGeneral
                                    ErrorType.WrongInput -> DialogInfoType.ErrorWrongCredentials
                                    else -> DialogInfoType.ErrorGeneral
                                },
                                isActive = true
                            )
                        }
                    }
                    is Resource.Loading -> {
                        signInState = signInState.copy(
                            isLoading = true
                        ).apply {
                            signInState.error = signInState.error.copy(
                                isActive = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        signInState = signInState.copy(
                            isLoading = false,
                            signInSuccess = true,
                        ).apply {
                            signInState.error = signInState.error.copy(
                                isActive = false
                            )
                        }
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
                            isLoading = false
                        ).apply {
                            error = UIError(
                                result.message!!,
                                dialogType = when (result.type) {
                                    ErrorType.IO -> DialogInfoType.ErrorNoConnection
                                    ErrorType.ServerError -> DialogInfoType.ErrorGeneral
                                    ErrorType.WrongInput -> DialogInfoType.ErrorWrongCredentials
                                    else -> DialogInfoType.ErrorGeneral
                                },
                                isActive = true
                            )
                        }
                    }
                    is Resource.Loading -> {
                        signInState = signInState.copy(
                            isLoading = true
                        ).apply {
                            error = error.copy(
                                isActive = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        // TODO what to do when success
                        signInState = signInState.copy(
                            isLoading = false,
                        ).apply {
                            error = error.copy(
                                isActive = false
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun clearError() {
        signInState.error = signInState.error.copy(
            isActive = false
        )
    }
}