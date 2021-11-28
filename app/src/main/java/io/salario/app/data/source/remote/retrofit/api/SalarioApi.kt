package io.salario.app.data.source.remote.retrofit.api

import io.salario.app.data.model.Paycheck
import io.salario.app.data.model.UserCreationResponse
import io.salario.app.data.model.UserData
import io.salario.app.data.source.remote.retrofit.model.UserCreationRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SalarioApi {
    @POST("users/create")
    suspend fun createNewUser(@Body userCreationBody: UserCreationRequestBody): UserCreationResponse

    @GET("users/userdata")
    suspend fun authenticateUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): UserData

    @GET("paycheck")
    suspend fun getPaycheckById(@Query("pcid") paycheckId: String): Paycheck
}