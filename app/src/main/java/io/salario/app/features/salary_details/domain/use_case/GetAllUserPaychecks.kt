package io.salario.app.features.salary_details.domain.use_case

import io.salario.app.core.util.Resource
import io.salario.app.features.salary_details.domain.repository.SalaryRepository
import kotlinx.coroutines.flow.Flow

class GetAllUserPaychecks(private val repository: SalaryRepository) {
    operator fun invoke(): Flow<Resource<out Any>> {
        return repository.getUserPaychecks()
    }
}