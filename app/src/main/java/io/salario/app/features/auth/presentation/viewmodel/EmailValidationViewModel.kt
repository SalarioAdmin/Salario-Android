package io.salario.app.features.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.ValidateUserCreation
import io.salario.app.features.auth.presentation.state.EmailValidationState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EmailValidationViewModel @Inject constructor(
    private val validateUserCreation: ValidateUserCreation,
) : ViewModel() {
    var emailValidationState by mutableStateOf(EmailValidationState())
        private set

    fun onValidateUserCreation(email: String, userCreationValidationToken: String) {
        validateUserCreation(email, userCreationValidationToken)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        emailValidationState = emailValidationState.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        emailValidationState = emailValidationState.copy(
                            errorMessage = "",
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        emailValidationState = emailValidationState.copy(
                            errorMessage = "",
                            isLoading = false,
                            shouldNavigateForward = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}