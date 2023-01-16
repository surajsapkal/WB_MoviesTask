package com.example.moviestask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "movies"
)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id : String,
    val title : String,
    val poster_path : String,
    val release_date : String
) : Serializable