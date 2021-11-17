package io.salario.app.data.source.remote.retrofit.api

import io.salario.app.data.model.Paycheck
import retrofit2.http.GET
import retrofit2.http.Query

interface SalaryApi {

    @GET("paycheck")
    suspend fun getPaycheckById(@Query("pcid") paycheckId: String) : Paycheck
}