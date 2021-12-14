package io.salario.app.features.auth.presentation.state

import io.salario.app.core.customui.state_holder.TextFieldState
import io.salario.app.core.util.getPasswordValidationError
import io.salario.app.core.util.isValidEmail

data class SignUpState(
    val isLoading: Boolean = false,
    val shouldNavigateForward: Boolean = false,
    val errorMessage: String = "",
    val firstNameInputState: TextFieldState = TextFieldState(
        validate = { firstName ->
            when {
                firstName.isEmpty() || firstName.isBlank() -> "First Name should not be empty."
                else -> null
            }
        }),
    val lastNameInputState: TextFieldState = TextFieldState(
        validate = { lastName ->
            when {
                lastName.isEmpty() || lastName.isBlank() -> "Last Name should not be empty."
                else -> null
            }
        }),
    val emailInputState: TextFieldState = TextFieldState(
        validate = { email ->
            when {
                email.isEmpty() || email.isBlank() -> "Email should not be empty"
                !isValidEmail(email) -> "Please enter a valid Email address"
                else -> null
            }
        }),
    val passwordInputState: TextFieldState = TextFieldState(
        validate = { password ->
            if (password.isEmpty() || password.isBlank()) {
                "Password should not be empty"
            } else {
                getPasswordValidationError(password)
            }
        })
)
