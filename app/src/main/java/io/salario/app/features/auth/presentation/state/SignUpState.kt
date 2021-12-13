package io.salario.app.features.auth.presentation.state

data class SignUpState(
    val isLoading: Boolean = false,
    var shouldNavigateToValidation: Boolean = false,
    val errorMessage: String = ""
)
