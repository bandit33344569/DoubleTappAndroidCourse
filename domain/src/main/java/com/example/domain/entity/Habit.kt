package com.example.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.example.domain.entity.Type
import com.example.domain.entity.Priority

@Parcelize
class Habit(
    val name: String,
    val description: String,
    val priority: Priority,
    val type: Type,
    val times: Int,
    val period: Int,
    var id: String = "",
    val date: Int = -1,
    val color: Int = 0,
    var doneDates: List<Int>
) :Parcelable

