package com.example.clickinggameproject

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val width = displaymetrics.widthPixels // 1080
        val height = displaymetrics.heightPixels // 1794

        var soundButton = findViewById<Button>(R.id.buttonSound)
        var randX = 0
        var randY = 0
        
        var gameScore = findViewById<TextView>(R.id.textViewGameScore)
        var score = 0
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

    }
}