package com.example.myapplication.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class HabitUid(
    val uid: String
) : Parcelable