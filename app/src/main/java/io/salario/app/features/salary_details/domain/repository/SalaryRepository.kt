package io.salario.app.features.salary_details.domain.repository

import io.salario.app.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {
    fun uploadPaycheck(pdfData: String): Flow<Resource<out Any>>
    fun getUserPaychecks(): Flow<Resource<out Any>>
}