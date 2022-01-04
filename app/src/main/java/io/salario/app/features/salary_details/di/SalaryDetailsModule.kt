package io.salario.app.features.salary_details.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.salario.app.BuildConfig
import io.salario.app.core.domain.use_case.GetAccessToken
import io.salario.app.core.domain.use_case.RefreshAccessToken
import io.salario.app.core.util.network.AuthInterceptor
import io.salario.app.features.salary_details.data.remote.api.SalaryApi
import io.salario.app.features.salary_details.domain.repository.SalaryRepository
import io.salario.app.features.salary_details.domain.repository.SalaryRepositoryImpl
import io.salario.app.features.salary_details.domain.use_case.GetAllUserPaychecks
import io.salario.app.features.salary_details.domain.use_case.UploadPaycheck
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SalaryDetailsModule {

    @Provides
    @Singleton
    fun provideSalaryApi(
        getAccessToken: GetAccessToken,
        refreshAccessToken: RefreshAccessToken
    ): SalaryApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }

        val authInterceptor = AuthInterceptor(getAccessToken, refreshAccessToken)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(SalaryApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SalaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: SalaryApi
    ): SalaryRepository = SalaryRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideUploadPaycheckUseCase(repository: SalaryRepository): UploadPaycheck {
        return UploadPaycheck(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPaychecksUseCase(repository: SalaryRepository): GetAllUserPaychecks {
        return GetAllUserPaychecks(repository)
    }
}