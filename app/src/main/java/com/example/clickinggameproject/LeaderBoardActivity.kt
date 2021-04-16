package com.example.clickinggameproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

class LeaderBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        var submitButton = findViewById<Button>(R.id.buttonSubmitScore)
        var backToMenuButton = findViewById<Button>(R.id.buttonMainMenu)

        var initialsText = findViewById<EditText>(R.id.editTextInitials)
        var initials = ""
        var yourScoreText = findViewById<TextView>(R.id.textViewYourScore)

        var filename = "leaderboards.txt"

        var lbNames = findViewById<TextView>(R.id.textViewNames)
        var lbScore = findViewById<TextView>(R.id.textViewScores)
        var lbRank = findViewById<TextView>(R.id.textViewRanks)

        // get the score from the intent that passes it and set it at the top of the screen
        val myScore = intent.getIntExtra("currentScore", 0)
        yourScoreText.append(myScore.toString())

        // save initials for the current player for when we have a high score
        initials = initialsText.toString()

        fun setInitialLeaderboard() {
            val data = "AAA 10\nBBB 9\nCCC 8\nDDD 8\nEEE 7"
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            }
            catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun readTextFromFileAndUpdateScreen() {
            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput(filename)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            var text: String? = null

            // if the file is empty this we set the default leaderboard to the text file
            // bufferedReader.ready() returns false when the file is empty
            if(bufferedReader.ready() == false) {
                setInitialLeaderboard()
            }
            // else read in each part of the textfile to the corresponding part on the screen
            else
            {
                var rank = 0
                // text obtained from bufferedReader is not null read in the next line
                while( { text = bufferedReader.readLine(); text } () != null) {
                    rank++
                    var initials = text?.substring(0, 3) // initials are the first 3 characters of the string
                    var fileScore = text?.substring(3) // score is the following characters after the initials
                    lbRank.append(rank.toString() + "\n")
                    lbNames.append(initials + "\n")
                    lbScore.append(fileScore + "\n")
                }
            }
        }

        /*
        * TODO: check for if player's score should be on the leaderboard ()
        *           if player's score should be on the leaderboards:
        *               that check if the player's score is less than the lowest score
        *               determine where it should be on the leaderboard
        *               update the leaderboards.txt file in the correct order with the new score
        *               update the screen with the new data in the txt file
        *           else
        *               set the textViewEnterName, editTextInitials, and buttonSubmitScore to invisible

        */

        backToMenuButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        // called first every time the activity starts to show the data in the textfile on the screen
        readTextFromFileAndUpdateScreen()
    }
}
