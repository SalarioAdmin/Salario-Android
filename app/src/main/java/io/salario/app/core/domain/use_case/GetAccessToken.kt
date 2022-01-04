package io.salario.app.core.domain.use_case

import dagger.Lazy
import io.salario.app.features.auth.domain.repository.AuthRepository

class GetAccessToken(
    private val repository: Lazy<AuthRepository>
) {
    suspend operator fun invoke(): String {
        return repository.get().getAccessToken()
    }
}