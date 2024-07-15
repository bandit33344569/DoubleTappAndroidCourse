package com.example.data.net

import okhttp3.Interceptor
import okhttp3.Response

private var TOKEN = "5b813314-dafa-422c-810b-6501c6fa5525"

class AuthInterceptor : Interceptor {

    companion object {
        private const val AUTH_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder().header(
            AUTH_HEADER,
            TOKEN,
        )
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}