package com.example.myapplication.repository

import okhttp3.Interceptor
import okhttp3.Response

private var TOKEN = "5b813314-dafa-422c-810b-6501c6fa5525"

class AuthInterceptor : Interceptor {

    companion object {
        private const val authHeader = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder().header(
            authHeader,
            TOKEN,
        )
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}