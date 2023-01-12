package com.example.moviestask.api

import com.example.moviestask.data.Movies
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

interface MoviesApi {

    //api_key = ac3d59542f8acc452d1ab728de5e8000
    @GET("movie/top_rated?")
    fun getMoviesList(@Query("api_key") api_key : String) : Call<Movies>

    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        var moviesApi : MoviesApi? = null
        fun getMoviesInstance() : MoviesApi{
            val gson = GsonBuilder().setLenient().create()
            if (moviesApi == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                moviesApi = retrofit.create(MoviesApi::class.java)
            }
            return moviesApi!!
        }

    }
}