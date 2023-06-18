package com.example.pelisk.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.pelisk.R
import com.example.pelisk.databinding.FragmentMovieBinding
import com.example.pelisk.movieapp.core.Resource
import com.example.pelisk.movieapp.data.model.Movie
import com.example.pelisk.movieapp.data.remote.MovieDataSource
import com.example.pelisk.movieapp.presentation.MovieVewModelFactory
import com.example.pelisk.movieapp.presentation.MovieViewModel
import com.example.pelisk.movieapp.repository.MovieRepositoryImpl
import com.example.pelisk.movieapp.repository.RetrofitClient
import com.example.pelisk.movieapp.ui.movie.adapters.MovieAdapter
import com.example.pelisk.movieapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.pelisk.movieapp.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.pelisk.movieapp.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding:FragmentMovieBinding
    //by: es un delegador, el cual es una persona o cosa que va a realizar un trabajo por mi.
    //esto se conoce como inyeccion de dependencias manual, ya que estamos desde un objeto inyectando
    //otro objeto
    private val viewModel by viewModels<MovieViewModel> { MovieVewModelFactory(MovieRepositoryImpl(
        MovieDataSource(RetrofitClient.webservice)
    )) }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
          when(result) {
              is Resource.Loading -> {
                  Log.d("LiveData", "Loading...")
                  binding.progressBar.visibility = View.VISIBLE
              }
              is Resource.Success -> {
                Log.d("LiveData",
                    "Upcoming:\n" +
                        "${result.data.first}\n" +
                        "TopRated:\n" +
                        "${result.data.second}\n"
                        +"Popular:\n" +
                        "${result.data.third}")
                  binding.progressBar.visibility = View.GONE
                  concatAdapter.apply {
                      addAdapter(0, UpcomingConcatAdapter(MovieAdapter(result.data.first.results, this@MovieFragment)))
                      addAdapter(1, TopRatedConcatAdapter(MovieAdapter(result.data.second.results, this@MovieFragment)))
                      addAdapter(2, PopularConcatAdapter(MovieAdapter(result.data.third.results, this@MovieFragment)))
                  }
                  binding.rvMovies.adapter = concatAdapter
              }
              is Resource.Failure -> {
                  Log.d("LiveDataError", "${result.exception}")
                  binding.progressBar.visibility = View.GONE
              }
          }
        })

    }

    override fun onMovieClick(movie: Movie) {
        Log.d("Movie", "onMovieClick: ${movie}")
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)

    }
}