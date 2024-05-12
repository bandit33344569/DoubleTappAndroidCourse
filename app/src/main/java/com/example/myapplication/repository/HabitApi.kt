package com.example.myapplication.repository

import com.example.myapplication.models.AddServerHabitBody
import com.example.myapplication.models.HabitDone
import com.example.myapplication.models.HabitUid
import com.example.myapplication.models.ServerHabit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitApi {
    @GET("habit")
    suspend fun getHabits(): List<ServerHabit>
    @PUT("habit")
    suspend fun saveHabit(@Body habit: ServerHabit): HabitUid
    @PUT("habit")
    suspend fun addHabit(@Body habit: AddServerHabitBody): HabitUid
    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun removeHabit(
        @Body uid: HabitUid
    )
    @POST("habit")
    suspend fun completeHabit(@Body habitDone: HabitDone)
}