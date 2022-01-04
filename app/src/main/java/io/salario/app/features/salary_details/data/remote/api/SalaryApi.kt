package io.salario.app.features.salary_details.data.remote.api

import io.salario.app.core.util.network.AuthorizationType
import io.salario.app.features.salary_details.data.remote.dto.PaycheckDto
import retrofit2.Response
import retrofit2.http.*

interface SalaryApi {
    @FormUrlEncoded
    @POST("upload_paycheck")
    suspend fun uploadPaycheck(
        @Field("pdfData") pdfData: String,
        @Tag authorization: AuthorizationType = AuthorizationType.ACCESS_TOKEN
    ): Response<Unit>

    @GET("paycheck/all")
    suspend fun getUserPaychecks(
        @Tag authorization: AuthorizationType = AuthorizationType.ACCESS_TOKEN
    ): List<PaycheckDto>

    companion object {
        const val BASE_URL = "http://192.168.2.77:3000/api/"
    }
}