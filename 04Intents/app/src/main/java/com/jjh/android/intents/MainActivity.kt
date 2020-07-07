package com.jjh.android.intents

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MAX_NUMBER = 10
    }

    var numberToGuess: Int
    private var userGuessTextField: EditText? = null

    init {
        numberToGuess = generateRandomNumber()
    }

    fun onShowAlarmButtonClick(v: View?) {
        // Issue the Intent to set the Alarm
        val i = Intent(AlarmClock.ACTION_SET_ALARM)
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userGuessTextField = findViewById<EditText>(R.id.guessEditText)
        userGuessTextField!!.setText("")
        val title = findViewById<TextView>(R.id.title)
        title.text = title.text.toString() + MAX_NUMBER
    }

    fun onGuessSubmit(view: View?) {
        val userInput = userGuessTextField!!.text.toString()
        validateAndCheckGuess(userInput)
    }

    private fun generateRandomNumber(): Int {
        val rand = Random()
        return rand.nextInt(MAX_NUMBER)
    }

    private fun validateAndCheckGuess(userInput: String) {
        try {
            val guessInput = userInput.toInt()
            if (guessInput < 1 || guessInput > MAX_NUMBER) {
                displayInvalidUserInputToast()
            } else {
                checkForCorrectGuess(guessInput)
            }
        } catch (e: NumberFormatException) {
            displayInvalidUserInputToast()
        }
    }

    private fun checkForCorrectGuess(guessInput: Int) {
        if (guessInput == numberToGuess) {
            displayCorrectAnswerToast()
            userGuessTextField!!.setText("")
            numberToGuess = generateRandomNumber()
        } else {
            displayHintToast(guessInput > numberToGuess)
        }
    }

    private fun displayCorrectAnswerToast() {
        Toast.makeText(
            this,
            "You guessed correctly! The number was $numberToGuess",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun displayInvalidUserInputToast() {
        Toast.makeText(
            this,
            "Invalid input (input must be between 1 and $MAX_NUMBER)",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun displayHintToast(higher: Boolean) {
        var message = "The correct answer is higher"
        if (higher) {
            message = "The correct answer is lower"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}