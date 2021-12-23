package io.salario.app.features.auth.presentation.state

import io.salario.app.core.shared_ui.state_holder.TextFieldState
import io.salario.app.core.util.isValidEmail

data class EmailValidationState(
    val isLoading: Boolean = false,
    var shouldNavigateForward: Boolean = false,
    val errorMessage: String = "",
    val emailInputState: TextFieldState = TextFieldState(
        validate = { email ->
            when {
                email.isEmpty() || email.isBlank() -> "Email should not be empty"
                !isValidEmail(email) -> "Please enter a valid Email address"
                else -> null
            }
        }),
    val tokenInputState: TextFieldState = TextFieldState(
        validate = { email ->
            when {
                email.isEmpty() || email.isBlank() -> "Token should not be empty."
                else -> null
            }
        })
)