package com.jjh.android.numberguessgame

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var userGuessEditText: EditText? = null
    private var numberToGuess: Int
    private val rand = Random()

    companion object {
        private const val MAX_NUMBER = 10
        private const val CORRECT_MSG = "You guessed correctly! The number was "
        private const val INVALID_MSG =
            "Invalid input (input must be between 1 and $MAX_NUMBER)"
        private const val HIGHER_MSG = "The correct answer is higher"
        private const val LOWER_MSG = "The correct answer is lower"
    }

    init {
        numberToGuess = generateRandomNumber()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userGuessEditText = findViewById<EditText>(R.id.guessEditText)
        val title = findViewById<TextView>(R.id.title)
        title.text = title.text.toString() + MAX_NUMBER
    }

    // Handle button click
    fun onGuessButtonClick(view: View?) {
        val userInput = userGuessEditText!!.text.toString()
        validateAndCheckGuess(userInput)
    }

    private fun generateRandomNumber(): Int {
        return rand.nextInt(MAX_NUMBER - 1) + 1
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
            userGuessEditText!!.setText("")
            numberToGuess = generateRandomNumber()
        } else {
            displayHintToast(guessInput > numberToGuess)
        }
    }

    private fun displayCorrectAnswerToast() {
        Toast.makeText(this, CORRECT_MSG + numberToGuess, Toast.LENGTH_LONG)
            .show()
    }

    private fun displayInvalidUserInputToast() {
        Toast.makeText(this, INVALID_MSG, Toast.LENGTH_SHORT).show()
    }

    private fun displayHintToast(higher: Boolean) {
        var message = HIGHER_MSG
        if (higher) {
            message = LOWER_MSG
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}