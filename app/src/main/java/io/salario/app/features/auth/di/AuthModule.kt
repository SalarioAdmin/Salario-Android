package io.salario.app.features.auth.di

import android.content.Context
import com.salario.app.BuildConfig
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.salario.app.core.util.network.AuthInterceptor
import io.salario.app.features.auth.data.local.datastore.AuthDataStoreManager
import io.salario.app.features.auth.data.remote.api.AuthApi
import io.salario.app.features.auth.domain.repository.AuthRepository
import io.salario.app.features.auth.domain.repository.AuthRepositoryImpl
import io.salario.app.features.auth.domain.use_case.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthApi(
        getAccessToken: GetAccessToken,
        refreshAccessToken: RefreshAccessToken
    ): AuthApi {
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
            .baseUrl(AuthApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthDataStoreManager(
        @ApplicationContext context: Context
    ): AuthDataStoreManager = AuthDataStoreManager(context)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        dataStoreManager: AuthDataStoreManager
    ): AuthRepository = AuthRepositoryImpl(api, dataStoreManager)

    @Provides
    @Singleton
    fun provideCreateUserUseCase(repository: AuthRepository): CreateUser {
        return CreateUser(repository)
    }

    @Provides
    @Singleton
    fun provideAuthenticateUserUseCase(repository: AuthRepository): AuthenticateUser {
        return AuthenticateUser(repository)
    }

    @Provides
    @Singleton
    fun provideResetPasswordRequestUseCase(repository: AuthRepository): ResetPasswordRequest {
        return ResetPasswordRequest(repository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthRepository): Logout {
        return Logout(repository)
    }

    @Provides
    @Singleton
    fun provideGetConnectedUserUseCase(repository: AuthRepository): GetConnectedUser {
        return GetConnectedUser(repository)
    }

    @Provides
    @Singleton
    fun provideGetAccessTokenUseCase(repository: Lazy<AuthRepository>): GetAccessToken {
        return GetAccessToken(repository)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(repository: Lazy<AuthRepository>): RefreshAccessToken {
        return RefreshAccessToken(repository)
    }
}