package com.jjh.android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    init {
        Log.d(TAG, "init{} block")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
        setContentView(R.layout.activity_main)
        Log.d(TAG, "checking for saved state")
        savedInstanceState?.run {
            val msg = getString("msg")
            Log.d(TAG, "onCreate() - $msg")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    // Restoring / Saving Dynamic State
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState()")
        val msg = savedInstanceState.getString("msg")
        Log.d(TAG, "onRestoreInstanceState() - $msg")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("msg", "Hello Message " + Date())
        Log.d(TAG, "onSaveInstanceState()")
    }

}