package com.example.pelisk.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pelisk.R
import com.example.pelisk.databinding.FragmentMovieBinding
import com.example.pelisk.movieapp.core.Resource
import com.example.pelisk.movieapp.data.remote.MovieDataSource
import com.example.pelisk.movieapp.presentation.MovieVewModelFactory
import com.example.pelisk.movieapp.presentation.MovieViewModel
import com.example.pelisk.movieapp.repository.MovieRepositoryImpl
import com.example.pelisk.movieapp.repository.RetrofitClient

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private lateinit var binding:FragmentMovieBinding
    //by: es un delegador, el cual es una persona o cosa que va a realizar un trabajo por mi.
    //esto se conoce como inyeccion de dependencias manual, ya que estamos desde un objeto inyectando
    //otro objeto
    private val viewModel by viewModels<MovieViewModel> { MovieVewModelFactory(MovieRepositoryImpl(
        MovieDataSource(RetrofitClient.webservice)
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        binding.progressBar.visibility = View.GONE

        viewModel.fetchUpcomingMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("LiveData", "Loading")
                }
                is Resource.Success -> {
                    Log.d("LiveData", "${result.data}")
                }
                is Resource.Failure<*> -> {
                    Log.d("LiveData", "${result.exception}")
                }
            }
        })
    }


}