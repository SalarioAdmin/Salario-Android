package io.salario.app.features.salary_details.domain.use_case

import io.salario.app.core.util.Resource
import io.salario.app.features.salary_details.domain.repository.SalaryRepository
import kotlinx.coroutines.flow.Flow

class UploadPaycheck(private val repository: SalaryRepository) {
    operator fun invoke(
        pdfData: String
    ): Flow<Resource<Unit>> {
        return repository.uploadPaycheck(pdfData)
    }
}