package com.example.data.di

import com.example.data.net.HabitServerApi
import com.example.data.net.NetworkClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModel {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return NetworkClient.getHttpClient()
    }

    @Singleton
    @Provides
    fun provideHabitServerAPI(client: OkHttpClient) : HabitServerApi {
        return NetworkClient.getHabitServerAPI(client)
    }
}