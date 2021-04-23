package com.example.clickinggameproject

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.random.Random

class testButtonsActivity : AppCompatActivity() {
    private var numOfBtns: Int = 0
    private var randX = 0
    private var randY = 0
    private val numberOfButtonsToCreate = 3
    private val timer: Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_buttons)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val width = displaymetrics.widthPixels // 1080
        val height = displaymetrics.heightPixels // 1794

        CreateButtonTimer(width, height)
    }

    fun CreateButtonTimer(screenX: Int, screenY: Int) {
        var timerTask = timerTask {
            run() {
                randX = Random.nextInt(30, screenX - 250)
                randY = Random.nextInt(90, screenY - 144)
                CreateButtons(randX, randY)
            }
        }
        timer.schedule(timerTask, 250, 1000)
    }

    fun CreateButtons(posx: Int, posy: Int) {
        this.runOnUiThread() {
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val btn = Button(this)
            btn.id = numOfBtns
            numOfBtns++
            btn.setBackgroundResource(R.drawable.coin)

            btn.x = posx.toFloat()
            btn.y = posy.toFloat()

            addContentView(btn, params)

            val _createdButton = findViewById<Button>(btn.id)
            _createdButton.setOnClickListener()
            {
                Toast.makeText(this, "Button clicked index = " + btn.id, Toast.LENGTH_SHORT).show()
            }

            if (numOfBtns == numberOfButtonsToCreate) {
                timer.cancel()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}