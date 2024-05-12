package com.example.myapplication.repository

import android.util.Log
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"
    val habitAPI: HabitApi

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(logInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        habitAPI = retrofit.create(HabitApi::class.java)
    }
}

suspend fun <T> retryRequest(delay: Long = 1000, func: suspend () -> T): T {
    while(true) {
        try {
            return func()
        } catch (e: Exception) {
            Log.e("RetryRequest", Thread.currentThread().id.toString(), e)
        }
        delay(delay)
    }
}