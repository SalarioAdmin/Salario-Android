package io.salario.app.data.source.remote.retrofit.model.body

data class UserAuthRequestBody(
    val email: String,
    val password: String
)