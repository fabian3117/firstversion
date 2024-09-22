package com.example.myapplication.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.0.36:3000"
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjY2ZDhiOGM0MmRmZTNlMWU0ZjNlOTBmZSIsImVtYWlsIjoiZmVkZXJpY29nb256YWxlekBzcG9ydGNsdWIudGVhbSIsInJvbGUiOiJvd25lciIsInNwaWNlIjoiMDRHWnFUQndOeW9BIiwibWVyY2hhbnQiOltdLCJzZWRlIjpbXSwiYnJhbmRfaWQiOiI2MzI4YWZiMzU5ZDA1MmU2ODE3YzIyMDAiLCJuZWdvY2lvX2NlbnRyYWwiOnRydWUsImRhdGUiOiIwNC8wOS8yMDI0JyIsImV4cGlyZSI6IjIwMjQtMDktMDUgMTk6NDc6NTMuMzk1MjgwIiwidmVyc2lvbiI6IjAuMS41In0.H7er7pl_3I5gf0xYuuiSzYO8aEMWCx8DhNEorrxYnpo")) // Reemplaza con tu token real
        .build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}
