package com.jjh.android.game.ui.drivers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jjh.android.game.R

import kotlinx.android.synthetic.main.fragment_drivers.*
import java.lang.NumberFormatException

class DriversFragment : Fragment() {

    companion object {
        private const val TAG = "SlideshowFragment"
    }

    private val slideshowViewModel by viewModels<DriversViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView()")
        return inflater.inflate(R.layout.fragment_drivers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        slideshowViewModel.drivers.observe(viewLifecycleOwner, Observer {
            (textSlideshow as TextView).text = it.toString()
        })
        getDriverDataButton.setOnClickListener {
            onGetDriversButtonClick()
        }
    }

    private fun onGetDriversButtonClick() {
        Log.d(TAG, "onGetDriversButtonClick()")
        try {
            val year = editTextYear.text.toString().toInt()
            slideshowViewModel.loadDriverData(year)
        } catch (exp: NumberFormatException) {
            Log.d(TAG, exp.localizedMessage)
            Toast.makeText(context, "You must provide a year", Toast.LENGTH_LONG).show()
        }
    }


}