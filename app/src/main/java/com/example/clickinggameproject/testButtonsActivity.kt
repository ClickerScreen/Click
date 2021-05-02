package com.example.clickinggameproject

import android.app.job.JobScheduler
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RestrictTo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.random.Random
import kotlin.coroutines.*

class testButtonsActivity : AppCompatActivity() {
    private var numOfBtns: Int = 0
    private var randX = 0
    private var randY = 0
    private val numberOfButtonsToCreate = 4
    private val timer: Timer = Timer()
    private var delayTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_buttons)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val width = displaymetrics.widthPixels // 1080
        val height = displaymetrics.heightPixels // 1794

        CreateButtonTimer(width, height)
    }

    val scope = MainScope()
    fun NotClicked(button: Button) = scope.launch {
        delay(delayTime)
        button.isVisible = false
    }

    val localScope = CoroutineScope(Dispatchers.Default)
    fun ButtonNotClicked(button: Button) = localScope.launch {
        delay(3000)
        button.isVisible = false
    }

    fun CreateButtonTimer(screenX: Int, screenY: Int) {
        val timerTask = timerTask {
            run() {
                randX = Random.nextInt(30, screenX - 250)
                randY = Random.nextInt(90, screenY - 144)
                CreateButtons(randX, randY)
            }
        }
        timer.schedule(timerTask, 250, 1000)
    }

    fun MoveButton(_createdButton: Button) {
        randX = Random.nextInt(30, 1080 - 250)
        randY = Random.nextInt(90, 1794 - 144)

        // change the position of the button
        _createdButton.x = randX.toFloat()
        _createdButton.y = randY.toFloat()
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
            var job = NotClicked(_createdButton)

            _createdButton.setOnClickListener()
            {
                job.cancel()

                if(job.isCancelled) {
                    job = NotClicked(_createdButton)
                }

                MoveButton(_createdButton)
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