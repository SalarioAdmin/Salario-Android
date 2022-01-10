package io.salario.app.features.auth.data.remote.dto.response

import io.salario.app.features.auth.domain.model.TokenPair

data class TokenPairDto(
    val accessToken: String?,
    val refreshToken: String?
) {
    fun toTokenPair() : TokenPair {
        return TokenPair(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
