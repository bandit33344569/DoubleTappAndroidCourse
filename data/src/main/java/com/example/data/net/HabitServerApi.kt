package com.example.data.net

import com.example.data.net.data.HabitDone
import com.example.data.net.data.ServerHabit
import com.example.domain.entity.HabitUid
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitServerApi {
    @GET("habit")
    suspend fun getHabits(): List<ServerHabit>

    @PUT("habit")
    suspend fun saveHabit(@Body habit: ServerHabit): HabitUid

    @PUT("habit")
    suspend fun addHabit(@Body habit: ServerHabit): HabitUid

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun removeHabit(
        @Body uid: HabitUid
    )

    @POST("habit_done")
    suspend fun completeHabit(@Body habitDone: HabitDone)

}