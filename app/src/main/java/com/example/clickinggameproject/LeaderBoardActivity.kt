package com.example.clickinggameproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class LeaderBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        var submitButton = findViewById<Button>(R.id.buttonSubmitScore)
        var backToMenuButton = findViewById<Button>(R.id.buttonMainMenu)

        var initialsText = findViewById<EditText>(R.id.editTextInitials)
        var yourScoreText = findViewById<TextView>(R.id.textViewYourScore)
        var filename = "leaderboards.txt"

        var lbNames = findViewById<TextView>(R.id.textViewNames)
        var lbScore = findViewById<TextView>(R.id.textViewScores)
        var lbRank = findViewById<TextView>(R.id.textViewRanks)

        class Player(var _name:String, var _score:Int) {
            var name:String = _name
            var score:Int = _score
        }

        var topPlayers = ArrayList<Player>()

        // get the score from the intent that passes it and set it at the top of the screen
        val myScore = intent.getIntExtra("currentScore", 0)
        yourScoreText.append(myScore.toString())

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
                lbRank.setText("")
                lbNames.setText("")
                lbScore.setText("")
                // text obtained from bufferedReader is not null read in the next line
                while( { text = bufferedReader.readLine(); text } () != null) {
                    rank++
                    var initials = text?.substring(0, 3) // initials are the first 3 characters of the string
                    var fileScore = text?.substring(4) // score is the following characters after the initials
                    lbRank.append(rank.toString() + "\n")
                    lbNames.append(initials + "\n")
                    lbScore.append(fileScore + "\n")
                }
            }
        }

        fun checkScore() {
            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput(filename)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            var text: String? = null

            // text obtained from bufferedReader is not null read in the next line
            while( { text = bufferedReader.readLine(); text } () != null) {
                var initials = text?.substring(0, 3) // initials are the first 3 characters of the string
                var fileScore = text?.substring(4) // score is the following characters after the initials
                topPlayers.add(Player(initials.toString(), fileScore!!.toInt())) //!! means assert non null
            }

            //TODO: check for if player's score should be on the leaderboard ()
            //if player's score should be on the leaderboards:
            for(i in topPlayers) {
                println(i.score)
                if(i.score <= myScore) {
                    //then check if the player's score is less than the lowest score
                    topPlayers.add(Player(initialsText.text.toString(), myScore))
                    //determine where it should be on the leaderboard
                    var sortedTopPlayers = topPlayers.sortedWith(compareByDescending { it.score })
                    //update the leaderboards.txt file in the correct order with the new score
                    var data = ""
                    var playerCount = 0
                    for (i in sortedTopPlayers) {
                        if(playerCount > 4) // save the top 5 scores
                            break
                        data += i.name + " " + i.score + "\n"
                        playerCount++
                    }
                    val fileOutputStream: FileOutputStream
                    try {
                        fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
                        fileOutputStream.write(data.toByteArray())
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //update the screen with the new data in the txt file
                    readTextFromFileAndUpdateScreen()
                    break
                }
            }
        }

        submitButton.setOnClickListener() {
            if(TextUtils.isEmpty(initialsText.getText().toString())) {
                Toast.makeText(applicationContext, "Enter your initials", Toast.LENGTH_SHORT).show()
            }
            else {
                checkScore()
                submitButton.visibility = Button.INVISIBLE
                initialsText.visibility = EditText.INVISIBLE
            }
        }

        backToMenuButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        submitButton.visibility = Button.VISIBLE
        initialsText.visibility = EditText.VISIBLE
        // called first every time the activity starts to show the data in the textfile on the screen
        readTextFromFileAndUpdateScreen() // comment this if we need to reset the txt file
        //setInitialLeaderboard() // un comment this when we need to reset the txt file
    }
}
