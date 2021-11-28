package io.salario.app.data.repo

import io.salario.app.data.model.UserCreationResponse
import io.salario.app.data.model.UserData
import io.salario.app.data.source.remote.retrofit.api.RetrofitClient
import io.salario.app.data.source.remote.retrofit.api.SalarioApi
import io.salario.app.data.source.remote.retrofit.model.UserCreationRequestBody

class AuthRepository {
    private val salarioApi: SalarioApi = RetrofitClient.salarioAApi

    suspend fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): UserCreationResponse {
        return salarioApi.createNewUser(
            UserCreationRequestBody(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )
        )
    }

    suspend fun authenticateUser(email: String, password: String): UserData {
        return salarioApi.authenticateUser(email, password)
    }
}