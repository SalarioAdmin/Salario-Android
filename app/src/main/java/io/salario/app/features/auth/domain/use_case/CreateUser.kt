package io.salario.app.features.auth.domain.use_case

import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class CreateUser(
    private val repository: AuthRepository
) {
    operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flow<Resource<out Any>> {
        return repository.createUser(firstName, lastName, email, password)
    }
}