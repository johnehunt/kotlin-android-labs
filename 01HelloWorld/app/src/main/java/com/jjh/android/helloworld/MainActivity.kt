package com.jjh.android.helloworld

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val txtView = findViewById<TextView>(R.id.label)
    }

    fun searchButtonHandler(v: View) {
        Log.d("MainActivity", "searchButtonHandler");
        findViewById<TextView>(R.id.label).text = "searchButtonHandler"
    }

    fun addButtonHandler(v: View?) {
        Log.d("MainActivity", "addButtonHandler");
        findViewById<TextView>(R.id.label).text = "addButtonHandler"
    }
}