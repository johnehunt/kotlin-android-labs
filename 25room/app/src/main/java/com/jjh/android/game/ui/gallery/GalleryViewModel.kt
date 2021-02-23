package com.jjh.android.game.ui.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jjh.android.game.db.DefaultSchedulerProvider
import com.jjh.android.game.db.HeroRepository
import io.reactivex.rxjava3.core.Observable

class GalleryViewModel : ViewModel() {

    companion object {
        private const val TAG = "GalleryViewModel"
    }

    lateinit var repository: HeroRepository

    var heroes: List<Hero> = listOf<Hero>()

    fun refresh(): Observable<List<Hero>> {
        Log.d(TAG, "refresh()")
        return repository.findAllHeroes()
            .observeOn(DefaultSchedulerProvider.ui())
            .doOnNext {
                heroes = it
            }
    }

    val size
        get() = heroes.size

    fun get(index: Int): Hero {
        return heroes[index]
    }

    fun addHero(hero: Hero): Observable<Long> {
        Log.d(TAG, "addHero($hero)")
        return repository
            .addHero(hero)
    }

    fun deleteHero(hero: Hero): Observable<Int> {
        Log.d(TAG, "deleteHero($hero)")
        return repository
            .deleteHero(hero)
    }

}