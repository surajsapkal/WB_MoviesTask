package com.example.moviestask.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviestask.const.Constant.Companion.TABLE_NAME
import com.example.moviestask.data.Movie
import com.example.moviestask.data.Results

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(result: Results) : Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllMovies() : LiveData<List<Movie>>

}