package io.salario.app.features.auth.presentation.state

data class SignInState(
    val isLoading: Boolean = false,
    val signInSuccess: Boolean = false,
    val resetPasswordSuccess: Boolean = false,
    val errorMessage: String = ""
)
