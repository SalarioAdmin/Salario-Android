package io.salario.app.data.repo

import io.salario.app.data.source.loacalassets.LocalAssetsDataSource
import io.salario.app.data.source.remote.retrofit.api.RetrofitClient
import io.salario.app.data.source.remote.retrofit.api.SalarioApi

class SalaryDataRepository(
    private val salarioApi: SalarioApi = RetrofitClient.salarioAApi
) {

    private val localAssetsDataSource = LocalAssetsDataSource()

    fun getPaycheckDataFromLocal() = localAssetsDataSource.getPaycheckData()

    suspend fun getPaycheckFromRemote() = salarioApi.getPaycheckById("1")
}