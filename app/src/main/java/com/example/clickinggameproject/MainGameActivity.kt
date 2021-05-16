package com.example.clickinggameproject

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.random.Random


class MainGameActivity : AppCompatActivity() {
    var counter: Int = 20
    private var numOfBtns: Int = 0
    private var randX = 0
    private var randY = 0
    private val numberOfButtonsToCreate = 8
    private val timer: Timer = Timer()
    private var delayTime: Long = 2000
    var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) // request the activity to have no title
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) // set the proper flags for the title
        supportActionBar?.hide() // hide the title bar at the top
        setContentView(R.layout.activity_main_game)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.layout)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        val buttonSound: MediaPlayer = MediaPlayer.create(this, R.raw.coin_collect)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val width = displaymetrics.widthPixels // 1080
        val height = displaymetrics.heightPixels // 1794
        var score = 0

        val countDown = findViewById<TextView>(R.id.countDown)

        var timer = object : CountDownTimer(20000, 1000)
        {
            override fun onFinish() {
                countDown.text = "Finished"
                val handler = Handler(Looper.myLooper()!!)
                handler.postDelayed({
                    val intent = Intent(this@MainGameActivity, LeaderBoardActivity::class.java)
                    //pass the score also to the leaderboards activity addition by Jesse
                    intent.putExtra("currentScore", points)
                    startActivity(intent)
                }, 1000)

                buttonSound.release()
            }

            override fun onTick(millisUnitlFinished: Long)
            {
                countDown.text = counter.toString()
                counter--
            }
        }.start()
        
        var gameScore = findViewById<TextView>(R.id.textViewGameScore)
        gameScore.setText("Score = " + points)

        CreateButtonTimer(width, height, gameScore, buttonSound)

        //commenting out for now since leaderboard activity can send back to main menu now
        //also set the button to invisible it can be removed when we are happy with how it functions
        //val boardButton = findViewById<Button>(R.id.leaderBoard)
        //boardButton.setOnClickListener() {
            //val intent = Intent(this, LeaderBoardActivity::class.java)
            //startActivity(intent)
        //}
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

    fun CreateButtonTimer(screenX: Int, screenY: Int, score: TextView, sound: MediaPlayer) {
        val timerTask = timerTask {
            run() {
                randX = Random.nextInt(0, screenX - 250)
                randY = Random.nextInt(0, screenY - 144)
                CreateButtons(randX, randY, score, sound)
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

    fun CreateButtons(posx: Int, posy: Int, score:TextView, sound: MediaPlayer) {
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
                try {
                    sound.start()
                } catch (e:IllegalStateException) {
                e.printStackTrace()
            }


                if(job.isCancelled)
                    job = NotClicked(_createdButton)

                // add to the score visualizer on the screen
                points  += 1
                score.setText("Score = " + points)

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