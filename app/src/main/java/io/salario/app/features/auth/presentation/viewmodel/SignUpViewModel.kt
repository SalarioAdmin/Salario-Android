package io.salario.app.features.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.model.UIError
import io.salario.app.core.shared_ui.composable.DialogInfoType
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.CreateUser
import io.salario.app.features.auth.presentation.state.SignUpState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUser: CreateUser
) : ViewModel() {
    var signUpState by mutableStateOf(SignUpState())
        private set

    fun onSignUp(firstName: String, lastName: String, email: String, password: String) {
        createUser(firstName, lastName, email, password)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        signUpState = signUpState.copy(
                            isLoading = false
                        ).apply {
                            error = UIError(
                                result.message!!,
                                DialogInfoType.ErrorNoConnection, // TODO find a way to identify type
                                isActive = true
                            )
                        }
                    }
                    is Resource.Loading -> {
                        signUpState = signUpState.copy(
                            isLoading = true
                        ).apply {
                            error = error.copy(
                                isActive = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        signUpState = signUpState.copy(
                            isLoading = false,
                            signUpSuccess = true
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
        signUpState.error = signUpState.error.copy(
            isActive = false
        )
    }
}