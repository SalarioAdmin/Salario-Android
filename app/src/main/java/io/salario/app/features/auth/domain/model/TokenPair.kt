package io.salario.app.features.auth.domain.model

data class TokenPair(
    val accessToken: String?,
    val refreshToken: String?
)
