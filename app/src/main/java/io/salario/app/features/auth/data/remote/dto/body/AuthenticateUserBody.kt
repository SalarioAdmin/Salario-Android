package io.salario.app.features.auth.data.remote.dto.body

data class AuthenticateUserBody(
    val email: String,
    val password: String
)
