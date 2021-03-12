package com.jjh.android.game.ui.guess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels

import com.jjh.android.game.R
import kotlinx.android.synthetic.main.fragment_guess_number.*

class GuessNumberFragment : Fragment() {

    private lateinit var invalidMessage: String
    private val viewModel by viewModels<GameNumberViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guess_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = titleTextView.text.toString() + GameNumberViewModel.MAX_NUMBER
        invalidMessage =
            "${getString(R.string.INVALID_MSG_PROMPT)} ${GameNumberViewModel.MAX_NUMBER})"

        guessButton.setOnClickListener {
            onGuessButtonClick()
        }
    }

    private fun onGuessButtonClick() {
        val userInput = userGuessEditText.text.toString()
        try {
            val guessInput = userInput.toInt()
            // Check for cheat mode input
            if (guessInput == 0) {
                displayCheatToast()
            } else {
                when (viewModel.checkGuess(guessInput)) {
                    GameNumberViewModel.CORRECT_GUESS -> {
                        messageTextView.text = getString(R.string.CORRECT_MSG) +
                                " ${viewModel.numberToGuess} ${getString(R.string.NEW_NUMBER_MSG)}"
                        resetGame()
                    }
                    GameNumberViewModel.INVALID_GUESS -> displayInvalidUserInputMessage()
                    GameNumberViewModel.GUESS_HIGHER -> displayHintMessage(GameNumberViewModel.GUESS_HIGHER)
                    GameNumberViewModel.GUESS_LOWER -> displayHintMessage(GameNumberViewModel.GUESS_LOWER)
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
        val hint = if (status == GameNumberViewModel.GUESS_HIGHER) {
            getString(R.string.LOWER_MSG)
        } else {
            getString(R.string.HIGHER_MSG)
        }
        messageTextView.text = hint
    }

    private fun displayCheatToast() {
        val message = "${getString(R.string.HINT_MSG)}  ${viewModel.numberToGuess}"
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}