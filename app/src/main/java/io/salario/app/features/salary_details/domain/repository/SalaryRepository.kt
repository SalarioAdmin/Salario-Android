package io.salario.app.features.salary_details.domain.repository

import io.salario.app.core.util.Resource
import io.salario.app.features.salary_details.domain.model.Paycheck
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {
    fun uploadPaycheck(pdfData: String): Flow<Resource<Paycheck>>
    fun getUserPaychecks(): Flow<Resource<List<Paycheck>>>
}