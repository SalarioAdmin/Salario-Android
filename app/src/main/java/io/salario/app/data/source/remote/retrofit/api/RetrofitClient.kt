package io.salario.app.data.source.remote.retrofit.api

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://192.168.2.77:3000/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val request: Request = chain.request()
            val response = chain.proceed(request)

            // TODO implement a good error handling
            when (response.code()) {
                500 -> {
                    Log.e("Retrofit client", "500")
                    return@Interceptor response
                }

                203 -> {
                    Log.e("Retrofit client", "203")
                    return@Interceptor response
                }

                404 -> {
                    Log.e("Retrofit client", "404")
                    return@Interceptor response
                }

                200 -> {
                    Log.e("Retrofit client", "200")
                    return@Interceptor response
                }
            }

            response
        })
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val salarioAApi: SalarioApi = getRetrofit().create(SalarioApi::class.java)
}