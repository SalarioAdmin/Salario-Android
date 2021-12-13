package io.salario.app.core.util.network

import android.util.Log
import com.auth0.android.jwt.JWT
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.GetAccessToken
import io.salario.app.features.auth.domain.use_case.RefreshAccessToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val getAccessToken: GetAccessToken,
    private val refreshAccessToken: RefreshAccessToken
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().signedRequest()
        return chain.proceed(request)
    }

    private fun Request.signedRequest() = when (AuthorizationType.fromRequest(this)) {
        AuthorizationType.ACCESS_TOKEN -> this.signWithAccessToken()
        AuthorizationType.NONE -> this
    }

    private fun Request.signWithAccessToken(): Request {
        var accessToken: String? = runBlocking { getAccessToken() }
        if (accessToken != null && accessToken.isNotEmpty()) {
            val jwt = JWT(accessToken)
            if (jwt.isExpired(0)) {
                accessToken = refreshToken()
            }
        } else {
            Log.w(TAG, "Access token not found.")
        }

        return if (accessToken != null) {
            newBuilder()
                .header(AUTHORIZATION, "$BEARER$accessToken")
                .build()
        } else {
            this
        }
    }

    private fun refreshToken(): String? {
        var newAccessToken: String? = null
        runBlocking {
            refreshAccessToken().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        newAccessToken = result.data!!
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Log.e(TAG, "Failed to refresh access token.")
                    }
                }
            }
        }

        return newAccessToken
    }

    companion object {
        const val TAG = "AuthInterceptor "
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "
    }
}