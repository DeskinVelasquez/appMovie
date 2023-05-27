package com.example.pelisk.movieapp.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.pelisk.movieapp.core.Resource
import com.example.pelisk.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

class MovieViewModel(private val repo: MovieRepository): ViewModel() {

    //para usar las extensiones de liveData habrá que implemetar la libreria en el gradle.app
    //el liveData va a ejecutar el hilo donde se llevará a cabo esa corrutina
    fun fetchUpcomingMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.getUpcomingMovies()))
        }catch (e: Exception) {

        }

    }

}

class MovieVewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}