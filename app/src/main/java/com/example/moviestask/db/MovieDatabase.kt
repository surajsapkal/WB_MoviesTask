package com.example.moviestask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviestask.data.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao() : MovieDao

    companion object{
        @Volatile
        private var instance : MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){ //  code inside this block cant be accessed by other thread at same time
            instance?: createDatabase(context).also{ instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                MovieDatabase::class.java,
                "movie_db.db"
            ).build()
    }
}