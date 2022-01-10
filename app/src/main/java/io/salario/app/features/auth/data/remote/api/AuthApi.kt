package io.salario.app.features.auth.data.remote.api

import io.salario.app.core.util.network.AuthorizationType
import io.salario.app.features.auth.data.remote.dto.body.*
import io.salario.app.features.auth.data.remote.dto.response.TokenPairDto
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("create_user")
    suspend fun createUser(
        @Body createUserBody: CreateUserBody,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): TokenPairDto

    @PATCH("authenticate_user")
    suspend fun authenticateUser(
        @Body authenticateUserBody: AuthenticateUserBody,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): TokenPairDto

    @POST("reset_password_request")
    suspend fun resetPasswordRequest(
        @Body requestResetPasswordBody: ResetPasswordRequestBody,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Response<Unit>

    @POST("reset_password")
    suspend fun resetPassword(
        @Body resetPasswordBody: ResetPasswordBody,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Response<Unit>

    @PATCH("refresh_token")
    suspend fun refreshAccessToken(
        @Body refreshToken: RefreshTokenBody,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): TokenPairDto

    @DELETE("logout")
    suspend fun logout(
        @Tag authorization: AuthorizationType = AuthorizationType.ACCESS_TOKEN
    ): Response<Unit>

    companion object {
        const val BASE_URL = "http://192.168.2.77:3000/auth/"
    }
}