package com.jjh.android.game.db

import android.app.Application
import android.util.Log
import com.jjh.android.game.ui.gallery.Hero
import io.reactivex.rxjava3.core.Observable

class HeroRepository(private val application: Application,
                     private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider) {

    companion object {
        private const val TAG = "HeroRepository"
    }

    private val heroDao: HeroDao =
        HeroRoomDatabase.getDatabase(application).heroDao()

    fun close() {
        HeroRoomDatabase.getDatabase(application).close()
    }

    fun addHero(friend: Hero): Observable<Long> {
        return Observable.create<Long> {
            val result = heroDao.insert(friend)
            it.onNext(result)
            it.onComplete()
        }.subscribeOn(schedulerProvider.newThread())
    }

    fun findAllHeroes(): Observable<List<Hero>> {
        return Observable.create<List<Hero>> {
            val results = heroDao.findAll()
            it.onNext(results)
            it.onComplete()
        }.subscribeOn(schedulerProvider.newThread())
    }

    fun findHeroById(id: Int): Observable<Hero> {
        return Observable.create<Hero> {
            Log.d(TAG, "findHeroById($id) - starting Observable")
            val friend = heroDao.findById(id)
            Log.d(TAG, "findHeroById($id) - search completed emitting data")
            it.onNext(friend)
            it.onComplete()
        }.subscribeOn(schedulerProvider.newThread())
    }

    fun deleteHero(friend: Hero): Observable<Int> {
        return Observable.create<Int> {
            val result = heroDao.delete(friend)
            it.onNext(result)
            it.onComplete()
        }.subscribeOn(schedulerProvider.newThread())
    }

    fun deleteHeroById(id: Int): Observable<Int> {
        return Observable.create<Int> {
            val result = heroDao.deleteById(id)
            it.onNext(result)
            it.onComplete()
        }.subscribeOn(schedulerProvider.newThread())
    }

}