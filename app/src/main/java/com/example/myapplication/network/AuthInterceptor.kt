package com.example.myapplication.network
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val modifiedRequest: Request = originalRequest.newBuilder()
            .addHeader("X-Access-Tokens", "$token")
            .build()

        return chain.proceed(modifiedRequest)
    }
}
