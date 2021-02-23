package com.jjh.android.game.ui.gallery

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper

import com.jjh.android.game.R

class HeroDialog(private val activity: Activity?,
                 private val listener: HeroDialogListener) :
    AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_25room)) {

    interface HeroDialogListener {
        fun onOK(id: Int, name: String, guesses: Int)
        fun onCancel()
    }

    override fun create(): AlertDialog {
        val dialogLayout: View =
            LayoutInflater.from(activity).inflate(R.layout.hero_dialog_layout, null)
        setView(dialogLayout)

        val idEditText = dialogLayout.findViewById<EditText>(R.id.editTextId)
        val nameEditText = dialogLayout.findViewById<EditText>(R.id.editTextName)
        val guessesEditText = dialogLayout.findViewById<EditText>(R.id.editTextGuesses)
        setPositiveButton("OK") {
                dialog, id -> listener.onOK(idEditText.text.toString().toInt(),
                                            nameEditText.text.toString(),
                                            guessesEditText.text.toString().toInt()) }
        setNegativeButton("Cancel") { dialog, id -> listener.onCancel() }
        return super.create()
    }

}