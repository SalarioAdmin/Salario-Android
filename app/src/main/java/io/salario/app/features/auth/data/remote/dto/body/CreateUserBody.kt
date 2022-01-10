package io.salario.app.features.auth.data.remote.dto.body

data class CreateUserBody(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
