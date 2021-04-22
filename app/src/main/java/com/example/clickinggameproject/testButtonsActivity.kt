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
import kotlin.random.Random

class testButtonsActivity : AppCompatActivity() {
    var numOfBtns: Int = 0
    var randX = 0
    var randY = 0
    val numberOfButtonsToCreate = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_buttons)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val width = displaymetrics.widthPixels // 1080
        val height = displaymetrics.heightPixels // 1794

        for(i in 0..numberOfButtonsToCreate) {
            randX = Random.nextInt(30, width - 250)
            randY = Random.nextInt(90, height - 144)
            CreateButtons(randX, randY)
        }
    }

    fun CreateButtons(posx: Int, posy: Int){
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
    }
}