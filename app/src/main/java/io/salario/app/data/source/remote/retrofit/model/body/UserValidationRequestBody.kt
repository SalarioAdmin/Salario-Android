package io.salario.app.data.source.remote.retrofit.model.body

data class UserValidationRequestBody(
    val email: String,
    val validationToken: String
)