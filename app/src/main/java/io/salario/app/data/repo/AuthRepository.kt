package io.salario.app.data.repo

import com.auth0.android.jwt.JWT
import io.salario.app.application.SalarioApplication
import io.salario.app.data.model.UserData
import io.salario.app.data.source.local.cache.Cache
import io.salario.app.data.source.local.datastore.DataStoreManager
import io.salario.app.data.source.remote.retrofit.api.RetrofitClient
import io.salario.app.data.source.remote.retrofit.api.SalarioApi
import io.salario.app.data.source.remote.retrofit.model.body.UserAuthRequestBody
import io.salario.app.data.source.remote.retrofit.model.body.UserCreationRequestBody
import io.salario.app.data.source.remote.retrofit.model.body.UserResetPasswordBody
import io.salario.app.data.source.remote.retrofit.model.body.UserValidationRequestBody
import io.salario.app.data.source.remote.retrofit.model.response.*
import kotlinx.coroutines.flow.collect

class AuthRepository {
    private val salarioApi: SalarioApi = RetrofitClient.salarioAApi
    private val cache = Cache
    private val dataStoreManager = DataStoreManager(SalarioApplication.INSTANCE)

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

    suspend fun validateNewUser(email: String, token: String): UserValidationResponse {
        return salarioApi.validateNewUser(UserValidationRequestBody(email, token))
    }

    suspend fun authenticateUser(email: String, password: String): UserDataResponse {
        return salarioApi.authenticateUser(UserAuthRequestBody(email, password))
    }

    suspend fun resetPassword(email: String): UserResetPasswordResponse {
        return salarioApi.resetPassword(UserResetPasswordBody(email))
    }

    suspend fun logout(): LogoutResponse {
        return salarioApi.logout()
    }

    fun getConnectedUser() = cache.userData

    fun saveConnectedUser(userData: UserData?) {
        cache.userData = userData
    }

    suspend fun isUserAuthenticated(): Boolean {
        var isConnected = false
        dataStoreManager.getAccessToken().collect {
            if (it.isNotEmpty()) {
                val accessToken = JWT(it)

                cache.userData = UserData(
                    firstName = accessToken.claims["firstName"]!!.asString()!!,
                    lastName = accessToken.claims["lastName"]!!.asString()!!,
                    email = accessToken.claims["lastName"]!!.asString()!!
                )

                isConnected = true
            }
        }

        return isConnected
    }
}