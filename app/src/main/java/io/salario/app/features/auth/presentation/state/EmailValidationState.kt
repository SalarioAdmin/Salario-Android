package io.salario.app.features.auth.presentation.state

data class EmailValidationState(
    var shouldNavigateToStatus: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)