package io.salario.app.features.auth.domain.use_case

import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ValidateUserCreation(
    private val repository: AuthRepository
) {
    operator fun invoke(
        email: String,
        userCreationValidationToken: String
    ): Flow<Resource<out Any>> {
        return repository.validateUserCreation(email, userCreationValidationToken)
    }
}