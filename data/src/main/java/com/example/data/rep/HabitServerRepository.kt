package com.example.data.rep

import com.example.data.db.HabitDao
import com.example.data.db.HabitEntity
import com.example.data.net.HabitServerApi
import com.example.data.net.data.ServerHabit
import com.example.data.net.retryRequest
import com.example.domain.entity.Habit
import com.example.domain.entity.HabitUid
import com.example.domain.net.ApiResponse
import com.example.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitServerRepository @Inject constructor(
    private val habitsDao: HabitDao,
    private val habitServerAPI: HabitServerApi
): IRepository {

    override fun getAllHabit(): Flow<List<Habit>> {
        return habitsDao.getAll().map { it.map { it.toHabit() } }
    }

    override suspend fun loadHabitFromServer() : ApiResponse<Unit> {
        return try {
            val response = retryRequest { habitServerAPI.getHabits() }
            response.forEach{
                updateHabitInLocalDatabase(HabitEntity.fromHabit(it.toHabit()))
            }
            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    private fun updateHabitInLocalDatabase(habit: HabitEntity){
        val habitFromDatabase = habitsDao.getHabitById(habit.id)

        if (habitFromDatabase == null) {
            habitsDao.insert(habit)
        } else if (habitFromDatabase.date < habit.date) {
            habitsDao.update(habit)
        }
    }

    override fun getHabitById(id: String): Habit {
        return habitsDao.getHabitById(id).toHabit()
    }

    override suspend fun createHabit(habit: Habit): ApiResponse<HabitUid> {
        return try {
            val response = retryRequest {
                habitServerAPI.saveHabit(ServerHabit.habitToHabitServer(habit))
            }

            val apiResponse = ApiResponse.Success(HabitUid(response.uid))

            habit.id = apiResponse.data.uid
            updateHabitInLocalDatabase(HabitEntity.fromHabit(habit))
            return apiResponse

        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }


    }

    override suspend fun editHabit(habit: Habit): ApiResponse<HabitUid> {
        return try {
            val response = retryRequest {
                habitServerAPI.saveHabit(ServerHabit.habitToHabitServer(habit)) }

            val apiResponse = ApiResponse.Success(HabitUid(response.uid))

            habit.id = apiResponse.data.uid
            return apiResponse

        } catch (e: java.lang.RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun removeHabit(habit: Habit) : ApiResponse<Unit>{
        return try {
            retryRequest { habitServerAPI.removeHabit(HabitUid(habit.id)) }
            habitsDao.delete(HabitEntity.fromHabit(habit))
            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun doneHabit(habit: Habit): ApiResponse<Unit> {
        TODO()
    }
}