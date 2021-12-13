package io.salario.app.features.auth.domain.use_case

import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class Logout(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<out Any?>> {
        return repository.logout()
    }
}