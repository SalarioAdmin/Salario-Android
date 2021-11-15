package io.salario.app.data.repo

import io.salario.app.data.source.loaclassets.LocalAssetsDataSource

class SalaryDataRepository() {
    private val localAssetsDataSource = LocalAssetsDataSource()

    fun getPaycheckData() = localAssetsDataSource.getPaycheckData()
}