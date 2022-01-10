package io.salario.app.features.salary_details.domain.repository

import android.util.Log
import dagger.hilt.android.scopes.ActivityScoped
import io.salario.app.core.util.ErrorType
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.repository.AuthRepositoryImpl
import io.salario.app.features.salary_details.data.remote.api.SalaryApi
import io.salario.app.features.salary_details.data.remote.dto.body.UploadPaycheckBody
import io.salario.app.features.salary_details.domain.model.Paycheck
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityScoped
class SalaryRepositoryImpl @Inject constructor(
    private val api: SalaryApi
) : SalaryRepository {
    override fun uploadPaycheck(pdfData: String): Flow<Resource<Paycheck>> = flow {
        emit(Resource.Loading())
        try {
            val paycheckDto = api.uploadPaycheck(UploadPaycheckBody(pdfData))
            emit(Resource.Success(paycheckDto.toPaycheck()))
        } catch (e: IOException) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            emit(
                Resource.Error(
                    "Looks like a connection error.\nPlease check your internet connection.",
                    type = ErrorType.IO
                )
            )
        } catch (e: HttpException) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            when (e.code()) {
                400 -> {
                    emit(
                        Resource.Error(
                            "Invalid Email or Password.",
                            type = ErrorType.WrongInput
                        )
                    )
                }

                500 -> {
                    emit(
                        Resource.Error(
                            "Something went wrong.\nPlease try again.",
                            type = ErrorType.ServerError
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            emit(
                Resource.Error(
                    "Something went wrong.\nPlease try again.",
                    type = ErrorType.ServerError
                )
            )
        }
    }

    override fun getUserPaychecks(): Flow<Resource<List<Paycheck>>> = flow {
        emit(Resource.Loading())
        try {
            val paycheckDtoList = api.getUserPaychecks()
            emit(Resource.Success(
                paycheckDtoList.map {
                    it.toPaycheck()
                }
            ))
        } catch (e: IOException) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            emit(
                Resource.Error(
                    "Looks like a connection error.\nPlease check your internet connection.",
                    type = ErrorType.IO
                )
            )
        } catch (e: HttpException) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            when (e.code()) {
                400 -> {
                    emit(
                        Resource.Error(
                            "Invalid Email or Password.",
                            type = ErrorType.WrongInput
                        )
                    )
                }

                500 -> {
                    emit(
                        Resource.Error(
                            "Something went wrong.\nPlease try again.",
                            type = ErrorType.ServerError
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(AuthRepositoryImpl.TAG, "Upload Paycheck failed due to ", e)
            emit(
                Resource.Error(
                    "Something went wrong.\nPlease try again.",
                    type = ErrorType.ServerError
                )
            )
        }
    }
}
