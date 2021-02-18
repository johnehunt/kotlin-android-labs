package com.jjh.android.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import kotlin.random.Random

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MAX_NUMBER = 10
        private const val MAX_NUMBER_OF_GUESSES = 4
    }

    private var numberToGuess: Int
    private var guessCount = 0
    private lateinit var invalidMessage: String

    init {
        numberToGuess = generateRandomNumber()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleTextView.text = titleTextView.text.toString() + MAX_NUMBER
        invalidMessage =
                "${getString(R.string.INVALID_MSG_PROMPT)} $MAX_NUMBER)"
    }

    private fun generateRandomNumber(): Int {
        return Random.nextInt(MAX_NUMBER) + 1
    }

    fun onGuessButtonClick(v: View) {
        val userInput = userGuessEditText.text.toString()
        try {
            val guessInput = userInput.toInt()
            // Check for cheat mode input
            if (guessInput == 0) {
                messageTextView.text = getString(R.string.HINT_MSG) + " " + numberToGuess
            } else {
                guessCount++

                // Check to see if input is valid
                if (guessInput < 1 || guessInput > MAX_NUMBER) {
                    displayInvalidUserInputMessage()
                } else {
                    // Check to see if number was guessed
                    checkForCorrectGuess(guessInput)
                }

                // Check for max number of guesses
                if (guessCount == MAX_NUMBER_OF_GUESSES) {
                    messageTextView.text = "${getString(R.string.MAX_MESSAGE_LIMIT_MSG)} " +
                            "$numberToGuess ${getString(R.string.NEW_NUMBER_MSG)}"
                    resetGame()
                }

                guessesLeftTextView.text = "${getString(R.string.GUESSES_LEFT_MSG)} " +
                        "${MAX_NUMBER_OF_GUESSES - guessCount}"

            }
        } catch (e: NumberFormatException) {
            displayInvalidUserInputMessage()
        }
    }

    private fun displayInvalidUserInputMessage() {
        messageTextView.text = invalidMessage
    }

    private fun checkForCorrectGuess(guessInput: Int) {
        if (guessInput == numberToGuess) {
            messageTextView.text = getString(R.string.CORRECT_MSG) +
                    " $numberToGuess ${getString(R.string.NEW_NUMBER_MSG)}"
            resetGame()
        } else {
            displayHintMessage(guessInput > numberToGuess)
        }
    }

    private fun resetGame() {
        userGuessEditText.setText("")
        numberToGuess = generateRandomNumber()
        guessCount = 0
    }

    private fun displayHintMessage(higher: Boolean) {
        var hint = if (higher) {
            getString(R.string.LOWER_MSG)
        } else {
            getString(R.string.HIGHER_MSG)
        }
        messageTextView.text = hint
    }

}