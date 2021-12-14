package io.salario.app.features.auth.domain.repository

import android.util.Log
import com.auth0.android.jwt.JWT
import dagger.hilt.android.scopes.ActivityScoped
import io.salario.app.core.domain.model.User
import io.salario.app.core.util.Resource
import io.salario.app.core.util.getUser
import io.salario.app.core.data.local.datastore.AuthDataStoreManager
import io.salario.app.features.auth.data.remote.api.AuthApi
import io.salario.app.features.auth.data.remote.dto.TokenPairDto
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
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            api.createUser(firstName, lastName, email, password)
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Create user failed due to ", e)
            emit(Resource.Error())
        } catch (e: HttpException) {
            Log.e(TAG, "Create user failed due to ", e)
            emit(Resource.Error())
        } catch (e: Exception) {
            Log.e(TAG, "Create user failed due to ", e)
            emit(Resource.Error())
        }
    }

    override fun validateUserCreation(
        email: String,
        userCreationValidationToken: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            api.validateUserCreation(email, userCreationValidationToken)
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Validate user failed due to ", e)
            emit(Resource.Error())
        } catch (e: HttpException) {
            Log.e(TAG, "Validate user failed due to ", e)
            emit(Resource.Error())
        } catch (e: Exception) {
            Log.e(TAG, "Validate user failed due to ", e)
            emit(Resource.Error())
        }
    }

    override fun authenticateUser(
        email: String,
        password: String
    ): Flow<Resource<TokenPairDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.authenticateUser(email, password)
            dataStoreManager.apply {
                response.accessToken?.let {
                    saveAccessToken(it)
                }
                response.refreshToken?.let {
                    saveRefreshToken(it)
                }
            }
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Authenticate user failed due to ", e)
            emit(
                Resource.Error(
                    "It looks like a connection error, check your internet connection."
                )
            )
        } catch (e: HttpException) {
            Log.e(TAG, "Authenticate user failed due to ", e)
            when (e.code()) {
                400 -> {
                    emit(Resource.Error("Incorrect Email or password."))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Authenticate user failed due to ", e)
            emit(Resource.Error("Something went wrong, Please try again."))
        }
    }

    override fun resetPasswordRequest(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            api.resetPasswordRequest(email)
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Reset password request failed due to ", e)
            emit(Resource.Error())
        } catch (e: HttpException) {
            Log.e(TAG, "Reset password request failed due to ", e)
            emit(Resource.Error())
        } catch (e: Exception) {
            Log.e(TAG, "Reset password request failed due to ", e)
            emit(Resource.Error())
        }
    }

    override fun resetPassword(resetPasswordToken: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            api.resetPassword(resetPasswordToken)
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Reset password failed due to ", e)
            emit(Resource.Error())
        } catch (e: HttpException) {
            Log.e(TAG, "Reset password failed due to ", e)
            emit(Resource.Error())
        } catch (e: Exception) {
            Log.e(TAG, "Reset password failed due to ", e)
            emit(Resource.Error())
        }
    }

    override fun refreshAccessToken(): Flow<Resource<String>> = flow {
        val refreshToken = dataStoreManager.getRefreshToken().first()
        if (refreshToken.isNotEmpty()) {
            try {
                val response = api.refreshAccessToken(refreshToken)
                response.accessToken?.let {
                    dataStoreManager.saveAccessToken(it)
                    emit(Resource.Success(it))
                } ?: run {
                    emit(Resource.Error<String>())
                }
            } catch (e: IOException) {
                Log.e(TAG, "Refresh token failed due to ", e)
                emit(Resource.Error())
            } catch (e: HttpException) {
                Log.e(TAG, "Refresh token failed due to ", e)
                emit(Resource.Error())
            } catch (e: Exception) {
                Log.e(TAG, "Refresh token failed due to ", e)
                emit(Resource.Error())
            }
        } else {
            emit(Resource.Error("Refresh token not found"))
        }
    }

    override fun logout(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            api.logout()
            dataStoreManager.clear()
            emit(Resource.Success())
        } catch (e: IOException) {
            Log.e(TAG, "Logout failed due to ", e)
            emit(Resource.Error())
        } catch (e: HttpException) {
            Log.e(TAG, "Logout failed due to ", e)
            emit(Resource.Error())
        } catch (e: Exception) {
            Log.e(TAG, "Logout failed due to ", e)
            emit(Resource.Error())
        }
    }

    override fun getConnectedUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val token = dataStoreManager.getAccessToken().first()
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

    companion object {
        const val TAG = "AuthRepository "
    }
}