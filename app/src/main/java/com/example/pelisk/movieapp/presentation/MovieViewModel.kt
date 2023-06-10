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
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            //para hacer tres llamadas al servidor al mismo tiempo, en este caso se usa Triple
            emit(Resource.Success(Triple(repo.getUpcomingMovies(), repo.getTopRatedMovies(), repo.getPopularMovies())))
        }catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }

}

class MovieVewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}

//si requiero realizar más llamadas al servidor al mismo tiempo, pueso usar esta data class= data class NTuple(n)<T1, T2, T3, Tn...>(val t1: T1, val t2: T2, val t3: T3, val tn: Tn...)
data class NTuple5<T1, T2, T3, T4, T5>(val t1: T1, val t2: T2, val t3: T3, val t4: T5, val t5: T5)