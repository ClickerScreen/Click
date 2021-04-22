package com.example.clickinggameproject

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainGameActivity : AppCompatActivity() {
    var counter: Int = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val width = displaymetrics.widthPixels // 1080
        val height = displaymetrics.heightPixels // 1794
        var score = 0

        var soundButton = findViewById<ImageButton>(R.id.buttonSound)
        var randX = 0
        var randY = 0

        val countDown = findViewById<TextView>(R.id.countDown)

        var timer = object : CountDownTimer(10000, 1000)
        {
            override fun onFinish() {
                countDown.text = "Finished"
                val handler = Handler(Looper.myLooper()!!)
                handler.postDelayed({
                    val intent = Intent(this@MainGameActivity, LeaderBoardActivity::class.java)
                    //pass the score also to the leaderboards activity addition by Jesse
                    intent.putExtra("currentScore", score)
                    startActivity(intent)
                }, 1000)
            }

            override fun onTick(millisUnitlFinished: Long)
            {
                countDown.text = counter.toString()
                counter--
            }
        }.start()
        
        var gameScore = findViewById<TextView>(R.id.textViewGameScore)
        gameScore.setText("Score = " + score)

        // change button position on button click
        soundButton.setOnClickListener{
            // add to the score visualizer on the screen
            score += 1
            gameScore.setText("Score = " + score)

            // generate new position
            randX = Random.nextInt(30, width - 250)
            randY = Random.nextInt(90, height - 144)

            // change the position of the button
            soundButton.x = randX.toFloat()
            soundButton.y = randY.toFloat()
        }

        //commenting out for now since leaderboard activity can send back to main menu now
        //also set the button to invisible it can be removed when we are happy with how it functions
        //val boardButton = findViewById<Button>(R.id.leaderBoard)
        //boardButton.setOnClickListener() {
            //val intent = Intent(this, LeaderBoardActivity::class.java)
            //startActivity(intent)
        //}
    }
}