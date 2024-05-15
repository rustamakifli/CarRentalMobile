package com.example.myapplication
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitService {
    @GET("api/v1/cars/")
    suspend fun getCars(): List<Car>
}

// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "http://139.162.147.132/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RetrofitService by lazy {
        println("Retrofit API initialized with base URL: $BASE_URL")

        retrofit.create(RetrofitService::class.java)
    }
}
