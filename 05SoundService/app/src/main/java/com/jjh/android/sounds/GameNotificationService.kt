package com.jjh.android.sounds

import android.R
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class GameNotificationService :
    IntentService(GameNotificationService::class.java.simpleName) {

    companion object {
        private const val CHANNEL_ID = "com.jjh.android.games"
        private const val TAG = "GameNotificationService"
    }

    init {
        Log.d(TAG, "constructor()")
    }

    private var notificationManager: NotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
        createNotificationChannel()
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent() - starting")
        val notification = NotificationCompat.Builder(
            this@GameNotificationService,
            CHANNEL_ID
        )
            .setContentTitle("GameNotificationService")
            .setContentText("This is a notification from the game!")
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setChannelId(CHANNEL_ID)
            .setNumber(10)
            .build()
        val notificationID = 101
        notificationManager!!.notify(notificationID, notification)
        Log.d(TAG, "onHandleIntent() - stopping")
    }

    fun createNotificationChannel() {
        Log.d(TAG, "createNotificationChannel()")
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(
                TAG,
                "createNotificationChannel() - setting up channel"
            )
            val name = "Notify Demo Info"
            val description = "EXAMPLE NOTIFICATION CHANNEL"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 40, 300, 200, 400)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager!!.createNotificationChannel(channel)
        }
    }

}