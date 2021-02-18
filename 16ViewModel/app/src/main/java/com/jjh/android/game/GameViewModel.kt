package com.jjh.android.game

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel: ViewModel() {

    companion object {
        const val MAX_NUMBER = 10
        const val MAX_NUMBER_OF_GUESSES = 4

        const val CORRECT_GUESS = 0
        const val GUESS_HIGHER = 1
        const val GUESS_LOWER = 2
        const val INVALID_GUESS = 3
    }

    var numberToGuess: Int
        private set

    var guessCount = 0
        private set

    init {
        numberToGuess = generateRandomNumber()
    }

    private fun generateRandomNumber(): Int {
        return Random.nextInt(MAX_NUMBER) + 1
    }

    fun resetGameData() {
        numberToGuess = generateRandomNumber()
        guessCount = 0
    }

    fun checkGuess(guess: Int): Int {
        guessCount++
        return if (guess < 1 || guess > MAX_NUMBER) {
            INVALID_GUESS
        } else if (guess == numberToGuess) {
            CORRECT_GUESS
        } else if (guess > numberToGuess) {
            GUESS_HIGHER
        } else {
            GUESS_LOWER
        }
    }

    val maxNumberOfGuessReached: Boolean
        get() = guessCount == MAX_NUMBER_OF_GUESSES

    val remainingGuesses: Int
        get() = MAX_NUMBER_OF_GUESSES - guessCount



}