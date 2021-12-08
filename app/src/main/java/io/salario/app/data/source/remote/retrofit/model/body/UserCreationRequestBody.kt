package io.salario.app.data.source.remote.retrofit.model.body

data class UserCreationRequestBody(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
