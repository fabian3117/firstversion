package com.example.myapplication.network

import com.example.myapplication.models.ModelUser
import com.example.myapplication.models.RequestUser
import com.example.myapplication.models.UserData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET



interface ApiService {
   /*
     @GET("/socios")
    suspend fun getInfoAboutUser(@Body user:RequestUser) : ModelUser
    @GET("/principal")
    suspend fun getPrincipal(): Call<String>*/
    @GET("/cd/perfil_socio?documento=36375767")
    suspend fun getDataUser(): Response<UserData>
}
