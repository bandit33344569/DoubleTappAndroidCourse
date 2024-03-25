package com.example.myapplication.habit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Habit(
    var name: String,
    var description: String,
    var priority: Priority,
    var type: Type,
    var times: Int,
    var period: Int
) : Parcelable {
}