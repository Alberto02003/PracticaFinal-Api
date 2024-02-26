package com.example.andorid_api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL =
    "http://192.168.0.146:8081"

interface GuaguaApiInterface {
    @GET("/Juegos")
    @Headers("Accept: application/json")
    suspend fun getAllJuego(): Response<List<Juegos>>
    @POST("/guaguas")
    suspend fun createJuego(@Body guagua: Juegos): Response<Void>
    @DELETE("/guaguas/{id}")
    suspend fun deleteJuego(@Path("id") id: Int): Response<Void>
    @PUT("/guaguas/{id}")
    suspend fun updateJuego(@Path("id") id: Int, @Body juego: Juegos): Response<Void>




}
val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
object GuaguaApi{
    val retrofitService: GuaguaApiInterface by lazy {
        retrofit.create(GuaguaApiInterface::class.java)
    }
}