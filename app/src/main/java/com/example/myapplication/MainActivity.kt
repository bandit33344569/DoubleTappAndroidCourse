package com.example.myapplication

import BaseActivity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : BaseActivity() {

    private lateinit var counterTextView: TextView
    private var counter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        updateCounter()
    }

    private fun updateCounter() {
        counterTextView = findViewById(R.id.counterTextView)
        counterTextView.text = counter.toString()
    }

    fun startSecondActivity(v: View) {
        val intent: Intent = Intent(this, SecondActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra("counter",counter)
        startActivity(intent);
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        counter += 1
        updateCounter()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Activity Lifecycle", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Activity Lifecycle", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity Lifecycle", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity Lifecycle", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity Lifecycle", "onDestroy()")
    }

}
