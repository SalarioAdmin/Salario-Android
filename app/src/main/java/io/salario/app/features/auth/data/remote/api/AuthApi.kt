package io.salario.app.features.auth.data.remote.api

import io.salario.app.core.util.network.AuthorizationType
import io.salario.app.features.auth.data.remote.dto.TokenPairDto
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @FormUrlEncoded
    @POST("auth/create_user")
    suspend fun createUser(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): TokenPairDto

    @FormUrlEncoded
    @POST("auth/authenticate_user")
    suspend fun authenticateUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): TokenPairDto

    @FormUrlEncoded
    @POST("auth/reset_password_request")
    suspend fun resetPasswordRequest(
        @Field("email") email: String,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Response<Unit>

    @FormUrlEncoded
    @POST("auth/reset_password")
    suspend fun resetPassword(
        @Field("resetPasswordToken") resetPasswordToken: String,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Response<Unit>

    @FormUrlEncoded
    @POST("auth/refresh_token")
    suspend fun refreshAccessToken(
        @Field("refreshToken") refreshToken: String,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): TokenPairDto

    @DELETE("auth/logout")
    suspend fun logout(
        @Tag authorization: AuthorizationType = AuthorizationType.ACCESS_TOKEN
    ): Response<Unit>

    companion object {
        const val BASE_URL = "http://192.168.2.79:3000/"
    }
}