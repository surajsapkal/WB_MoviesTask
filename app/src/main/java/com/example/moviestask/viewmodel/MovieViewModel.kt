package com.example.moviestask.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviestask.const.Constant.Companion.API_KEY
import com.example.moviestask.const.Resource
import com.example.moviestask.data.Results
import com.example.moviestask.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app) {

    val movies : MutableLiveData<Resource<Results>> = MutableLiveData()
    private var moviesResponse : Results? = null

    init {
        getMovies(API_KEY)
    }

    private fun getMovies(api_key : String) = viewModelScope.launch {
        safeMoviesCall(api_key)
    }

    fun savedMovie(result: Results) = viewModelScope.launch {
        movieRepository.upsert(result)
    }

    fun getOfflineMovies() = movieRepository.getOfflineMovies()

    private suspend fun safeMoviesCall (api_key : String){
        movies.postValue(Resource.Loading())
        try {
            // network call
            if (hasInternetConnection()){
                val response = movieRepository.getMovies(api_key)
                movies.postValue(handleMovieResponse(response))
            }else{
                movies.postValue(Resource.Error("No internet connection"))
            }
        }catch (t : Throwable){
            // exception/ error
            when(t){
                is IOException -> movies.postValue(Resource.Error("Network Failure"))
                else -> movies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleMovieResponse(response: Response<Results>) : Resource<Results>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                if (moviesResponse == null){
                    moviesResponse = resultResponse
                }
                return Resource.Success(moviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection() : Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            val activeNetwork = connectivityManager.activeNetwork ?: return true
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

        }else{

            @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}