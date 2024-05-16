//package com.example.myapplication
//import retrofit2.http.GET
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.Call
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.POST
//
//// get cars
//interface RetrofitService {
//    @GET("api/v1/cars/")
//    suspend fun getCars(): List<Car>
//}
//
//object RetrofitClient {
//<<<<<<< HEAD
//    private const val BASE_URL = "http://139.162.147.132/"
//=======
//    private const val BASE_URL = "http://138.68.111.59/"
//>>>>>>> 0bf0d627ccf34a430c4599eee29a77952c2a3f35
//
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val api: RetrofitService by lazy {
//        println("Retrofit API initialized with base URL: $BASE_URL")
//
//        retrofit.create(RetrofitService::class.java)
//    }
//}
//
//// login calling
//interface ApiService {
//    @FormUrlEncoded
//    @POST("login/")
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<LoginResponse>
//}


package com.example.myapplication.api_services
import com.example.myapplication.data.Car
import com.example.myapplication.data.LoginResponse
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// get cars
interface RetrofitService {
    @GET("api/v1/cars/")
    suspend fun getCars(): List<Car>
}

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

// login calling
interface ApiService {
    @FormUrlEncoded
    @POST("login/")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}