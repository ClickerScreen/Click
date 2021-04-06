package com.example.clickinggameproject

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.exp

class MainMenuActivity : AppCompatActivity() {

    protected lateinit var player: MediaPlayer
    protected var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val blinkingText:TextView = findViewById<TextView>(R.id.blink)
        blinkingText.blink()
    }

    override fun onStart() {
        super.onStart()
        player = MediaPlayer.create(this, R.raw.song)
        player.isLooping = true
        player.start()
    }

    override fun onResume() {
        super.onResume()
        player.seekTo(position)
    }

    override fun onStop() {
        super.onStop()
        player.stop()
        player.release()
    }

    override fun onPause() {
        super.onPause()
        if (player != null) {
            player.pause()
            position = player.currentPosition
            if (isFinishing) {
                player.stop()
                player.release()
           }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return super.dispatchTouchEvent(ev)
    }

    private fun View.blink(
            times: Int = Animation.INFINITE,
            duration: Long = 300L,
            offset: Long = 100L,
            minAlpha: Float = 0.0f,
            maxAlpha: Float = 1.0f,
            repeatMode: Int = Animation.REVERSE
    ) {
        startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
            it.duration = duration
            it.startOffset = offset
            it.repeatMode = repeatMode
            it.repeatCount = times
        })
    }
}

