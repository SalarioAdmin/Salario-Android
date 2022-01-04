package io.salario.app.core.domain.use_case

import dagger.Lazy
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class RefreshAccessToken(
    private val repository: Lazy<AuthRepository>
) {
    operator fun invoke(): Flow<Resource<String>> {
        return repository.get().refreshAccessToken()
    }
}