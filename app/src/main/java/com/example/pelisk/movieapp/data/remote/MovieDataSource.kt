package com.example.pelisk.movieapp.data.remote

import com.example.pelisk.movieapp.aplication.AppConstans
import com.example.pelisk.movieapp.data.model.MovieList
import com.example.pelisk.movieapp.repository.WebService

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstans.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstans.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstans.API_KEY)
}