package io.salario.app.features.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.CreateUser
import io.salario.app.features.auth.presentation.state.SignUpState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUser: CreateUser,
) : ViewModel() {
    var signUpState by mutableStateOf(SignUpState())
        private set

    fun onSignUp(firstName: String, lastName: String, email: String, password: String) {
        createUser(firstName, lastName, email, password)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        signUpState = signUpState.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        signUpState = signUpState.copy(
                            errorMessage = "",
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        signUpState = signUpState.copy(
                            errorMessage = "",
                            isLoading = false,
                            shouldNavigateForward = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}