package com.example.pelisk.movieapp.data.remote

import com.example.pelisk.movieapp.aplication.AppConstans
import com.example.pelisk.movieapp.data.model.MovieList
import com.example.pelisk.movieapp.repository.WebService

class MovieDataSource(private val webService: WebService) {

    //suspend o corrutinas, una función suspend significa que hasta que no vaya hasta el servidor el
    //flujo de codigo no continua, suspend en como un countdownlactn para las funciones

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstans.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstans.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstans.API_KEY)
}