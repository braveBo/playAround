package com.redfin.hw.network

import com.google.gson.GsonBuilder
import com.redfin.hw.data.FoodTruck
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://data.sfgov.org/"

private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create()).build()

interface ApiService {
    @GET("/resource/jjew-r69b.json?dayorder=5")
    suspend fun getFoodTrucks(): List<FoodTruck>
}

object FoodTruckApi {
    val apiService:ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}