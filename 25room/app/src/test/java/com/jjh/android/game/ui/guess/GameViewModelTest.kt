package com.jjh.android.game.ui.guess

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        viewModel = GameViewModel()
    }

    @Test
    fun resetGameData() {
        val numberToGuess = viewModel.numberToGuess
        assertEquals(4, viewModel.remainingGuesses)
        if (numberToGuess != 10)
            viewModel.checkGuess(numberToGuess + 1)
        else
            viewModel.checkGuess(numberToGuess - 1)
        viewModel.resetGameData()
        assertEquals(4, viewModel.remainingGuesses)
    }

    @Test
    fun testCheckGuessWhenCorrect() {
        val numberToGuess = viewModel.numberToGuess
        val result = viewModel.checkGuess(numberToGuess)
        assertEquals(GameViewModel.CORRECT_GUESS, result)
    }

    @Test
    fun testCheckGuessWhenLower() {
        var numberToGuess = viewModel.numberToGuess
        while (numberToGuess == 1) {
            viewModel.resetGameData()
            numberToGuess = viewModel.numberToGuess
        }
        val result = viewModel.checkGuess(numberToGuess - 1)
        assertEquals(GameViewModel.GUESS_LOWER, result)
    }

    @Test
    fun testCheckGuessWhenHigher() {
        var numberToGuess = viewModel.numberToGuess
        while (numberToGuess == 10) {
            viewModel.resetGameData()
            numberToGuess = viewModel.numberToGuess
        }
        val result = viewModel.checkGuess(numberToGuess + 1)
        assertEquals(GameViewModel.GUESS_HIGHER, result)
    }

    @Test
    fun testCheckGuessWhenInvalid() {
        val result = viewModel.checkGuess(-1)
        assertEquals(GameViewModel.INVALID_GUESS, result)
    }

    @Test
    fun testRemainingGuessesBehaviour() {
        val numberToGuess = viewModel.numberToGuess
        assertEquals(4, viewModel.remainingGuesses)
        if (numberToGuess != 10)
            viewModel.checkGuess(numberToGuess + 1)
        else
            viewModel.checkGuess(numberToGuess - 1)
        assertEquals(3, viewModel.remainingGuesses)
    }

    @Test
    fun testMaxnumberReached() {
        val numberToGuess = viewModel.numberToGuess
        repeat(4) {
            if (numberToGuess != 10)
                viewModel.checkGuess(numberToGuess + 1)
            else
                viewModel.checkGuess(numberToGuess - 1)
        }
        assertTrue(viewModel.maxNumberOfGuessReached)
    }
}