package com.example.myapplication.habit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Habit(
    var Name: String,
    var Description: String,
    var Priority: Priority,
    var Type: Type,
    var Times: Int,
    var Period: Int
) : Parcelable