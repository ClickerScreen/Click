package com.example.clickinggameproject

import android.app.ActionBar
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AbsoluteLayout
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import java.security.KeyStore
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var soundButton = findViewById<Button>(R.id.buttonSound)
        var randUp = 0// = Random.nextInt(0, 900)
        var randDown = 0// = Random.nextInt(0, 500)
        var wentUp = false
        var wentDown = false
        //val wentLeft = false
        //val wentRight = false

        soundButton.setOnClickListener{
            // remove the button from screen
            soundButton.visibility = View.INVISIBLE
            // reset button position to where it's initial position
            //soundButton.x
            if(wentUp)
                soundButton.y += randUp
            if(wentDown)
                soundButton.y -= randDown
            // reset bool flagswentUp = false
            wentDown = false
            //var wentLeft = false
            //var wentRight = false
            // generate new position
            //var randX = Random.nextInt(0, 300)
            randUp = Random.nextInt(0, 900)
            randDown = Random.nextInt(0, 500)

            var choice = Random.nextFloat()
            //      determine if position should go up or down
            if(choice >= 0.5) {
                // go up
                soundButton.y -= randUp
                //soundButton.y -= randUp
                wentUp = true
                // determine if position should go left or right
            }
            else {
                // go down
                soundButton.y += randDown
                //soundButton.y += randDown
                wentDown = true
                // determine if position should go left or right
            }

            // change button position to the new position

            // Sound button can go to the left or right 300 units
            // Sound button can go to the top 900 units
            // Sound button can go to the bottom 500 units
            //soundButton.left += 100
            //soundButton.top -= 100
            //soundButton.right += 100
            //soundButton.bottom -= 100

            // show button
            soundButton.visibility = View.VISIBLE
        }

    }
}