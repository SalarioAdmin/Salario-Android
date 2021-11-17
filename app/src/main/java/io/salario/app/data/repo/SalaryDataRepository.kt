package io.salario.app.data.repo

import io.salario.app.data.source.loaclassets.LocalAssetsDataSource
import io.salario.app.data.source.remote.retrofit.api.RetrofitClient
import io.salario.app.data.source.remote.retrofit.api.SalaryApi

class SalaryDataRepository(
    private val salaryApi: SalaryApi = RetrofitClient.salaryAApi
) {

    private val localAssetsDataSource = LocalAssetsDataSource()

    fun getPaycheckDataFromLocal() = localAssetsDataSource.getPaycheckData()

    suspend fun getPaycheckFromRemote() = salaryApi.getPaycheckById("1")
}