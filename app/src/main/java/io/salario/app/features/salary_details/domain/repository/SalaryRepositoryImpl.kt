package io.salario.app.features.salary_details.domain.repository

import android.util.Log
import dagger.hilt.android.scopes.ActivityScoped
import io.salario.app.core.util.Resource
import io.salario.app.core.util.network.getError
import io.salario.app.features.auth.domain.repository.AuthRepositoryImpl
import io.salario.app.features.salary_details.data.local.db.dao.PaycheckDao
import io.salario.app.features.salary_details.data.remote.api.SalaryApi
import io.salario.app.features.salary_details.data.remote.dto.body.UploadPaycheckBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityScoped
class SalaryRepositoryImpl @Inject constructor(
    private val api: SalaryApi,
    private val dao: PaycheckDao
) : SalaryRepository {
    override fun uploadPaycheck(pdfData: String): Flow<Resource<out Any>> = flow {
        emit(Resource.Loading())
        try {
            val paycheckDto = api.uploadPaycheck(UploadPaycheckBody(pdfData))
            dao.insertPaycheck(paycheckDto.toPaycheckEntity())
        } catch (e: IOException) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            e.getError()
        } catch (e: HttpException) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            emit(e.getError())
        }

        val updatedPaychecks = dao.getPaychecks().map { it.toPaycheck() }
        emit(Resource.Success(updatedPaychecks))
    }

    override fun getUserPaychecks(): Flow<Resource<out Any>> = flow {
        emit(Resource.Loading())

        val paychecks = dao.getPaychecks().map { it.toPaycheck() }
        emit(Resource.Loading(paychecks))

        try {
            val paychecksDto = api.getUserPaychecks()
            dao.deletePaychecksByIds(paychecksDto.map { it.id })
            dao.insertPaychecks(paychecksDto.map { it.toPaycheckEntity() })
        } catch (e: IOException) {
            Log.e(AuthRepositoryImpl.TAG, "Get User paychecks failed due to ", e)
            e.getError()
        } catch (e: HttpException) {
            Log.e(AuthRepositoryImpl.TAG, "Get User paychecks failed due to ", e)
            emit(e.getError())
        }

        val updatedPaychecks = dao.getPaychecks().map { it.toPaycheck() }
        emit(Resource.Success(updatedPaychecks))
    }
}
