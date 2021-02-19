package com.jjh.android.game.ui.guess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.provider.AlarmClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jjh.android.game.R
import com.jjh.android.game.service.MusicPlayerService
import kotlinx.android.synthetic.main.fragment_guess_number.*


class GuessNumberFragment : Fragment() {

    companion object {
        private const val TAG = "GuessNumberFragment"
    }

    private lateinit var invalidMessage: String
    private val viewModel by viewModels<GameViewModel>()
    private lateinit var service: MusicPlayerService

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
            onGuessButtonClick()
        }

        openAlaramButton.setOnClickListener {
            onOpenAlarmButtonClick()
        }

        playMusicButton.setOnClickListener {
            onPlayMusicButtonClick()
        }

        stopMusicButton.setOnClickListener {
            stopMusicButtonClick()
        }
        stopMusicButton.isEnabled = false
    }

    private fun onPlayMusicButtonClick() {
        Log.d(TAG, "onPlayMusicButtonClick()")
        try {
            val intent = Intent(this.context, MusicPlayerService::class.java)
            activity?.bindService(intent, ServiceConnectionHandler(), Context.BIND_AUTO_CREATE)
            playMusicButton.isEnabled = false
        } catch (exp: Exception) {
            Log.d(TAG, "onPlayMusicButtonClick() - ${exp.localizedMessage}")
        }
    }

    private fun stopMusicButtonClick() {
        Log.d(TAG, "onPlayMusicButtonClick()")
        service.stop()
        playMusicButton.isEnabled = true
        stopMusicButton.isEnabled = false
    }

    private fun onOpenAlarmButtonClick() {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
        startActivity(intent)
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

    private fun displayCheatToast() {
        val message = "${getString(R.string.HINT_MSG)}  ${viewModel.numberToGuess}"
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    // Service Connection Listener
    private inner class ServiceConnectionHandler : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            binder: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val demoBinder = binder as MusicPlayerService.MusicServiceBinder
            service = demoBinder.service
            Log.d("ServiceConnection", "MusicPlayerService bound")
            service.start()
            stopMusicButton.isEnabled = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("ServiceConnection", "onServiceDisconnected")
        }

    }
}