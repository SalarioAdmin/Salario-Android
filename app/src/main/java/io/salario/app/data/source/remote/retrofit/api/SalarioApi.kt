package io.salario.app.data.source.remote.retrofit.api

import io.salario.app.data.source.remote.retrofit.model.body.UserAuthRequestBody
import io.salario.app.data.source.remote.retrofit.model.body.UserCreationRequestBody
import io.salario.app.data.source.remote.retrofit.model.body.UserResetPasswordBody
import io.salario.app.data.source.remote.retrofit.model.body.UserValidationRequestBody
import io.salario.app.data.source.remote.retrofit.model.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SalarioApi {
    @POST("auth/createUser")
    suspend fun createNewUser(@Body userCreationBody: UserCreationRequestBody)
            : UserCreationResponse

    @POST("auth/validateNewUser")
    suspend fun validateNewUser(@Body userCreationBody: UserValidationRequestBody)
            : UserValidationResponse

    @POST("auth/authenticateUser")
    suspend fun authenticateUser(@Body userAuthBody: UserAuthRequestBody)
            : UserDataResponse

    @POST("auth/resetPassword")
    suspend fun resetPassword(@Body resetPasswordBody: UserResetPasswordBody)
            : UserResetPasswordResponse

    @POST("auth/logout")
    suspend fun logout(): LogoutResponse
}