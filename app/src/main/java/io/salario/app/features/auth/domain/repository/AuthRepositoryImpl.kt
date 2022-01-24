package io.salario.app.features.auth.domain.repository

import android.util.Log
import com.auth0.android.jwt.JWT
import dagger.hilt.android.scopes.ActivityScoped
import io.salario.app.core.domain.model.User
import io.salario.app.core.util.Resource
import io.salario.app.core.util.getUser
import io.salario.app.core.util.network.getError
import io.salario.app.features.auth.data.local.datastore.AuthDataStoreManager
import io.salario.app.features.auth.data.remote.api.AuthApi
import io.salario.app.features.auth.data.remote.dto.body.*
import io.salario.app.features.auth.data.remote.dto.response.TokenPairDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityScoped
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val dataStoreManager: AuthDataStoreManager
) : AuthRepository {

    override fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flow<Resource<out Any>> = flow {
        emit(Resource.Loading())
        try {
            val tokensResponse = api.createUser(
                CreateUserBody(firstName, lastName, email, password)
            )
            saveTokens(tokensResponse)
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Authenticate user failed due to ", e)
            emit(e.getError())
        } catch (e: HttpException) {
            Log.e(TAG, "Authenticate user failed due to ", e)
            emit(e.getError())
        }
    }

    override fun authenticateUser(
        email: String,
        password: String
    ): Flow<Resource<out Any>> = flow {
        emit(Resource.Loading())
        try {
            val tokensResponse = api.authenticateUser(AuthenticateUserBody(email, password))
            saveTokens(tokensResponse)
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Authenticate user failed due to ", e)
            emit(e.getError())
        } catch (e: HttpException) {
            Log.e(TAG, "Authenticate user failed due to ", e)
            emit(e.getError())
        }
    }

    override fun resetPasswordRequest(email: String): Flow<Resource<out Any>> = flow {
        emit(Resource.Loading())
        try {
            api.resetPasswordRequest(ResetPasswordRequestBody(email))
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Reset password request failed due to ", e)
            emit(e.getError())
        } catch (e: HttpException) {
            Log.e(TAG, "Reset password request failed due to ", e)
            emit(e.getError())
        }
    }

    override fun resetPassword(resetPasswordToken: String): Flow<Resource<Any>> = flow {
        emit(Resource.Loading())
        try {
            api.resetPassword(ResetPasswordBody(resetPasswordToken))
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Reset password failed due to ", e)
            emit(e.getError())
        } catch (e: HttpException) {
            Log.e(TAG, "Reset password failed due to ", e)
            emit(e.getError())
        }
    }

    override fun refreshAccessToken(): Flow<Resource<out Any>> = flow {
        val refreshToken = getAccessToken()
        try {
            val tokenPair = api.refreshAccessToken(RefreshTokenBody(refreshToken))
            saveTokens(tokenPair)
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Refresh token failed due to ", e)
            emit(e.getError())
        } catch (e: HttpException) {
            Log.e(TAG, "Refresh token failed due to ", e)
            emit(e.getError())
        }
    }

    override fun logout(): Flow<Resource<out Any>> = flow {
        emit(Resource.Loading())
        try {
            api.logout()
            dataStoreManager.clear()
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Logout failed due to ", e)
            emit(e.getError())
        } catch (e: HttpException) {
            Log.e(TAG, "Logout failed due to ", e)
            emit(e.getError())
        }
    }

    override fun getConnectedUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val token = getAccessToken()
        if (token.isNotEmpty()) {
            val user = JWT(token).getUser()
            emit(Resource.Success(user))
        } else {
            emit(Resource.Error("No connected user."))
        }
    }

    override suspend fun getAccessToken(): String {
        return dataStoreManager.getAccessToken().first()
    }

    private suspend fun saveTokens(tokenPairDto: TokenPairDto) {
        dataStoreManager.apply {
            tokenPairDto.accessToken?.let {
                saveAccessToken(it)
            }
            tokenPairDto.refreshToken?.let {
                saveRefreshToken(it)
            }
        }
    }

    companion object {
        const val TAG = "AuthRepository "
    }
}