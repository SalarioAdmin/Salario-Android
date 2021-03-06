package io.salario.app.features.auth.domain.use_case

import io.salario.app.core.model.User
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetConnectedUser(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<User>> {
        return repository.getConnectedUser()
    }
}