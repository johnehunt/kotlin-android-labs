package com.jjh.android.game.ui.gallery

import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    val heroes: List<Hero> = listOf(
        Hero("Gryff Smith", 3),
        Hero("Adam Jones", 2),
        Hero("Jasmine Davies", 2),
        Hero("Phoebe Byrne", 2),
    )

    val size
        get() = heroes.size

    fun get(index: Int) = heroes[index]
}