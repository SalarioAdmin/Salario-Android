package io.salario.app.features.auth.domain.repository

import io.salario.app.core.domain.model.User
import io.salario.app.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createUser(
        firstName: String, lastName: String, email: String, password: String
    ): Flow<Resource<out Any>>

    fun authenticateUser(email: String, password: String): Flow<Resource<out Any>>
    fun resetPasswordRequest(email: String): Flow<Resource<out Any>>
    fun resetPassword(resetPasswordToken: String): Flow<Resource<out Any>>
    fun refreshAccessToken(): Flow<Resource<out Any>>
    fun logout(): Flow<Resource<out Any?>>
    fun getConnectedUser(): Flow<Resource<User>>
    suspend fun getAccessToken(): String
}