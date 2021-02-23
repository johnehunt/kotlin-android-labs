package com.jjh.android.game.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jjh.android.game.ui.gallery.Hero

@Database(entities = [Hero::class], version = 1)
abstract class HeroRoomDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao

    companion object {
        private var Singleton: HeroRoomDatabase? = null
        fun getDatabase(context: Context): HeroRoomDatabase {
            if (Singleton == null) {
                synchronized(HeroRoomDatabase::class.java) {
                    if (Singleton == null) {
                        Singleton = Room.databaseBuilder(
                            context.applicationContext,
                            HeroRoomDatabase::class.java,
                            "game_hero_db"
                        ).build()
                    }
                }
            }
            return Singleton!!
        }
    }
}