package com.jjh.android.game.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

import com.jjh.android.game.ui.gallery.Hero

@Dao
interface HeroDao {

    @Insert
    fun insert(hero: Hero): Long

    @Insert
    fun insertAll(vararg guesses: Hero): List<Long>

    @Update
    fun update(hero: Hero): Int

    @Query("SELECT * FROM heroes")
    fun findAll(): List<Hero>

    @Query("SELECT * FROM heroes WHERE id=:id")
    fun findById(id: Int): Hero

    @Delete
    fun delete(hero: Hero): Int

    @Query("DELETE FROM heroes WHERE id = :id")
    fun deleteById(id: Int): Int

    @Delete
    fun deleteAll(vararg guessses: Hero): Int

}
