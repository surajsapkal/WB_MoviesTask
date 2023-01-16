package com.example.moviestask.api

import com.example.moviestask.data.Results
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/top_rated?")
    suspend fun getMoviesList(@Query("api_key") api_key : String) : Response<Results>

}