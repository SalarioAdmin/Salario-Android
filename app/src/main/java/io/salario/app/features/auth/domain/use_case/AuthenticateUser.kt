package io.salario.app.features.auth.domain.use_case

import io.salario.app.core.util.Resource
import io.salario.app.features.auth.data.remote.dto.TokenPairDto
import io.salario.app.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthenticateUser(
    private val repository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<TokenPairDto>> {
        return repository.authenticateUser(email, password)
    }
}