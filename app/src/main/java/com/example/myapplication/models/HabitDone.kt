package com.example.myapplication.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class HabitDone(
    val date: Int,
    val habitUid: String
) : Parcelable