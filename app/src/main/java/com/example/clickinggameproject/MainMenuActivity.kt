package com.example.clickinggameproject

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity()
{

    protected lateinit var player: MediaPlayer
    protected var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) // request the activity to have no title
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) // set the proper flags for the title
        supportActionBar?.hide() // hide the title bar at the top
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
        val intent = Intent(this, MainGameActivity::class.java)
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

