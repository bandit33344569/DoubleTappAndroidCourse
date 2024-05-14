package com.example.presentator.di

import android.app.Application
import android.content.Context
import com.example.presentator.di.DaggerAppComponent
class HabitTrackerApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        var mInstance: HabitTrackerApplication? = null

        fun getInstance(): HabitTrackerApplication? = mInstance

        fun getContext(): Context? = mInstance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .context(context = this)
            .build()

        mInstance = this
    }
}