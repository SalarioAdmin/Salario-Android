package io.salario.app.data.source.remote.retrofit.model.response

data class UserDataResponse(
    val success: Boolean,
    val message: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
