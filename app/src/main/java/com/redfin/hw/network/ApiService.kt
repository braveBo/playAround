package com.redfin.hw.network

import com.google.gson.GsonBuilder
import com.redfin.hw.data.FoodTruck
import com.redfin.hw.data.ImageItem
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


private val interceptor = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}
private val okHttpclient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

private val retrofit = Retrofit.Builder().baseUrl("https://data.sfgov.org")
    .addConverterFactory(GsonConverterFactory.create()).build()

interface ApiService {
    @GET("/resource/jjew-r69b.json")
    suspend fun getFoodTrucks(@Query("dayorder") dayorder:Int = 5): List<FoodTruck>
}

object FoodTruckApi {
    val apiService:ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

private const val BASE_URL2 = "https://picsum.photos"

private val retrofit2 = Retrofit.Builder().baseUrl(BASE_URL2)
    .client(okHttpclient)
    .addConverterFactory(GsonConverterFactory.create()).build()

interface ApiService2 {
    @GET("v2/list")
    suspend fun getImages(@Query("page") page:Int = 1, @Query("limit") limit: Int = 100): Response<List<ImageItem>>
}
object ImageApi {
    val apiService:ApiService2 by lazy {
        retrofit2.create(ApiService2::class.java)
    }
}