package com.example.myapplication

import BaseActivity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class SecondActivity : BaseActivity() {

    private lateinit var squareOfCounterTextView: TextView
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val counter = intent.getIntExtra("counter",0)
        setContentView(R.layout.second_activity)
        updateSquare(counter)
    }

    private fun updateSquare(counter: Int) {
        squareOfCounterTextView = findViewById(R.id.squareOfCounterTextView)
        squareOfCounterTextView.text = (counter * counter).toString()
    }

    fun returnToMain(v: View) {
        finish()
    }

}
