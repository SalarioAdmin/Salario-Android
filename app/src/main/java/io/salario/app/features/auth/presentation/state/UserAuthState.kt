package io.salario.app.features.auth.presentation.state

import io.salario.app.core.domain.model.User

data class UserAuthState(
    val isConnected: Boolean = false,
    var shouldLogout: Boolean = false,
    val isLoading: Boolean = false,
    val userData: User? = null,
    val errorMessage: String = ""
)