package com.jjh.android.game.ui.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.jjh.android.game.R

/**
 * View used to display information about a play
 */
class HeroViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.heroes_list_layout, parent, false)) {

    companion object {
        private const val TAG = "PlayViewHolder"
    }

    private val titleView = itemView.findViewById<TextView>(R.id.heroName)
    private val yearView = itemView.findViewById<TextView>(R.id.guesses)


    fun bind(hero: Hero) {
        Log.d(TAG, "bind($hero)")
        titleView.text = hero.name
        yearView.text = hero.guesses.toString()
    }

}