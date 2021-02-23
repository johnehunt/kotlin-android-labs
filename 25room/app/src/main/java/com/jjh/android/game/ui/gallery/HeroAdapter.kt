package com.jjh.android.game.ui.gallery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jjh.android.game.db.DefaultSchedulerProvider

/**
 * Provide binding from the hero list to HeroViewHolders displayed
 * within a {@link RecyclerView}.
 */
class HeroAdapter(val context: Context, private val viewModel: GalleryViewModel) : RecyclerView.Adapter<HeroViewHolder>() {

    companion object {
        private const val TAG = "HeroAdapter"
    }

    /**
     * Called when RecyclerView needs a new {@link HeroViewHolder} to represent
     * a Hero.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        val inflater = LayoutInflater.from(parent.context)
        return HeroViewHolder(inflater, parent)
    }

    /**
     * Called by RecyclerView to display the HeroViewHolder at the specified position.
     */
    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder($position)")
        viewModel.get(position)?.run {
            holder.bind(this)
        }
    }

    /**
     *
     * Returns the total number of Heroes in the data set held by the view model.
     */
    override fun getItemCount(): Int = viewModel.size

    fun addHero(hero: Hero) {
        Log.d(TAG, "addHero()")
        viewModel.addHero(hero)
            .observeOn(DefaultSchedulerProvider.ui())
            .subscribe{
                Log.d(TAG,"updating adapter data set changed")
                viewModel.refresh().subscribe {
                    notifyDataSetChanged()
                }
            }
    }

    fun deleteHero(position: Int) {
        val hero = viewModel.get(position)
        viewModel.deleteHero(hero)
            .observeOn(DefaultSchedulerProvider.ui())
            .subscribe{
                Log.d(TAG,"updating adapter after deletion")
                viewModel.refresh().subscribe {
                    notifyDataSetChanged()
                }
            }
    }

}

