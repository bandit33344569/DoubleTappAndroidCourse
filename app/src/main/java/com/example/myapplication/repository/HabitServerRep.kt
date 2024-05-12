package com.example.myapplication.repository

import com.example.myapplication.database.HabitDao
import com.example.myapplication.habit.Habit
import com.example.myapplication.models.AddServerHabitBody
import com.example.myapplication.models.HabitUid
import com.example.myapplication.models.ServerHabit

class HabitServerRep(private val habitsDao: HabitDao) {

    suspend fun loadHabitFromServer() : ApiResponse<Unit> {
        return try {
            val response = retryRequest { NetworkClient.habitAPI.getHabits() }
            response.forEach{
                val habit = it.toHabit()
                updateHabitInLocalDatabase(habit)
            }
            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    private  fun updateHabitInLocalDatabase(habit: Habit){
        val habitFromDatabase = habitsDao.getHabitById(habit.Id)

        if (habitFromDatabase == null) {
            habitsDao.insert(habit)
        } else if (habitFromDatabase.date < habit.date) {
            habitsDao.update(habit)
        }
    }



     suspend fun editHabit(habit: Habit): ApiResponse<HabitUid> {
        return try {
            val response = retryRequest {
                NetworkClient.habitAPI.saveHabit(ServerHabit.habitToHabitServer(habit)) }

            val apiResponse = ApiResponse.Success(HabitUid(response.uid))

            habit.Id = apiResponse.data.uid
            updateHabitInLocalDatabase(habit)
            return apiResponse

        } catch (e: java.lang.RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    suspend fun addHabit(habit: Habit): ApiResponse<HabitUid> {
        return try {
            val response = retryRequest {
                NetworkClient.habitAPI.addHabit(AddServerHabitBody.habitToHabitServer(habit)) }

            val apiResponse = ApiResponse.Success(HabitUid(response.uid))

            habit.Id = apiResponse.data.uid
            return apiResponse

        } catch (e: java.lang.RuntimeException) {
            ApiResponse.Error(e)
        }
    }


     suspend fun removeHabit(habit: Habit) : ApiResponse<Unit>{
        return try {
            retryRequest { NetworkClient.habitAPI.removeHabit(HabitUid(habit.Id)) }
            habitsDao.delete(habit)
            ApiResponse.Success(data = Unit)
        } catch (e: java.lang.RuntimeException) {
            ApiResponse.Error(e)
        }
    }

}