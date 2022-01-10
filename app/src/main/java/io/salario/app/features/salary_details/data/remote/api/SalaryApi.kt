package io.salario.app.features.salary_details.data.remote.api

import io.salario.app.core.util.network.AuthorizationType
import io.salario.app.features.salary_details.data.remote.dto.body.UploadPaycheckBody
import io.salario.app.features.salary_details.data.remote.dto.response.PaycheckDto
import retrofit2.http.*

interface SalaryApi {
    @POST("upload_paycheck")
    suspend fun uploadPaycheck(
        @Body uploadPaycheckBody: UploadPaycheckBody,
        @Tag authorization: AuthorizationType = AuthorizationType.ACCESS_TOKEN
    ): PaycheckDto

    @GET("paycheck/all")
    suspend fun getUserPaychecks(
        @Tag authorization: AuthorizationType = AuthorizationType.ACCESS_TOKEN
    ): List<PaycheckDto>

    companion object {
        const val BASE_URL = "http://192.168.2.77:3000/api/"
    }
}