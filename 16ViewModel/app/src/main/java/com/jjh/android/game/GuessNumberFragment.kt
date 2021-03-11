package com.jjh.android.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import kotlinx.android.synthetic.main.fragment_guess_number.*

class GuessNumberFragment : Fragment() {

    private lateinit var invalidMessage: String
    private val viewModel by viewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guess_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = titleTextView.text.toString() + GameViewModel.MAX_NUMBER
        invalidMessage =
            "${getString(R.string.INVALID_MSG_PROMPT)} ${GameViewModel.MAX_NUMBER})"

        guessButton.setOnClickListener {
            onGuessButtonClick(it)
        }
    }

    fun onGuessButtonClick(v: View) {
        val userInput = userGuessEditText.text.toString()
        try {
            val guessInput = userInput.toInt()
            // Check for cheat mode input
            if (guessInput == 0) {
                messageTextView.text = getString(R.string.HINT_MSG) + " " + viewModel.numberToGuess
            } else {
                when (viewModel.checkGuess(guessInput)) {
                    GameViewModel.CORRECT_GUESS -> {
                        messageTextView.text = getString(R.string.CORRECT_MSG) +
                                " ${viewModel.numberToGuess} ${getString(R.string.NEW_NUMBER_MSG)}"
                        resetGame()
                    }
                    GameViewModel.INVALID_GUESS -> displayInvalidUserInputMessage()
                    GameViewModel.GUESS_HIGHER -> displayHintMessage(GameViewModel.GUESS_HIGHER)
                    GameViewModel.GUESS_LOWER -> displayHintMessage(GameViewModel.GUESS_LOWER)
                }

                // Check for max number of guesses
                if (viewModel.maxNumberOfGuessReached) {
                    messageTextView.text = "${getString(R.string.MAX_MESSAGE_LIMIT_MSG)} " +
                            "${viewModel.numberToGuess} ${getString(R.string.NEW_NUMBER_MSG)}"
                    resetGame()
                }

                guessesLeftTextView.text = "${getString(R.string.GUESSES_LEFT_MSG)} " +
                        "${viewModel.remainingGuesses}"

            }
        } catch (e: NumberFormatException) {
            displayInvalidUserInputMessage()
        }
    }

    private fun displayInvalidUserInputMessage() {
        messageTextView.text = invalidMessage
    }


    private fun resetGame() {
        userGuessEditText.setText("")
        viewModel.resetGameData()
    }

    private fun displayHintMessage(status: Int) {
        val hint = if (status == GameViewModel.GUESS_HIGHER) {
            getString(R.string.LOWER_MSG)
        } else {
            getString(R.string.HIGHER_MSG)
        }
        messageTextView.text = hint
    }
}