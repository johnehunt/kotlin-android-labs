package com.jjh.android.game.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjh.android.game.R
import com.jjh.android.game.db.HeroRepository
import com.jjh.android.game.ui.gallery.HeroDialog.HeroDialogListener
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : Fragment() {

    companion object {
        private const val TAG = "GalleryFragment"
    }

    private val galleryViewModel by viewModels<GalleryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        galleryViewModel.repository = HeroRepository(requireActivity().application)
        galleryViewModel.refresh().subscribe {
            hero_recycler_view.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = HeroAdapter(context, galleryViewModel)
                val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback((hero_recycler_view.adapter as HeroAdapter)))
                itemTouchHelper.attachToRecyclerView(this)
            }
        }

        addButton.setOnClickListener {
            HeroDialog(activity, object : HeroDialogListener {
                override fun onOK(id: Int, name: String, guess: Int) {
                    Log.d(TAG, "The user tapped OK, input is $id $name $guess")
                    val hero = Hero(id, name, guess)
                    (hero_recycler_view.adapter as HeroAdapter).addHero(hero)
                }

                override fun onCancel() {
                    Log.d(TAG, "The user tapped Cancel")
                }
            }).show()
        }

    }
}