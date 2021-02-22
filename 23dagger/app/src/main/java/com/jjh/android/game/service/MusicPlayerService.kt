package com.jjh.android.game.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import com.jjh.android.game.R

class MusicPlayerService: Service() {

    companion object {
        private const val TAG = "MusicPlayerService"
    }

    // Set up binder information
    inner class MusicServiceBinder : Binder() {
        val service: MusicPlayerService
            get() = this@MusicPlayerService
    }

    private val binder: IBinder = MusicServiceBinder()

    private lateinit var mediaPlayer: MediaPlayer

    // Ensures music is played in a single thread that can be reused
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    // Lifecycle methods
    override fun onCreate() {
        Log.d(TAG, "onCreate()")
        mediaPlayer = MediaPlayer.create(this, R.raw.song)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind()")
        return binder
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        } catch (exp: Exception) {
            Log.d(TAG, "onCreate() - ${exp.localizedMessage}")
        }
        super.onDestroy()
    }

    fun start() {
        executor.execute(Runnable { mediaPlayer.start() })
    }

    // Shared behaviour
    fun stop() {
        executor.execute(Runnable {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        })
    }

}