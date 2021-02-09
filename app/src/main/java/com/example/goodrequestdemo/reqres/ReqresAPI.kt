package com.example.goodrequestdemo.reqres

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ReqresAPI {

    @GET("users")
    fun getUsersForPage(@Query("page") page: Int, @Query("per_page") perPage: Int = 10, @Query("delay") delay: Int = 1): Single<RequresResponse>

    @GET("users/{userId}")
    fun getUserDetail(@Path("userId") userId: Long, @Query("delay") delay: Int = 0): Single<ReuresSingleResponse>


    companion object {
        private const val BASE_URL = "https://reqres.in/api/"

        val INSTANCE: ReqresAPI

        init {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            INSTANCE = retrofit.create(ReqresAPI::class.java)
        }
    }
}