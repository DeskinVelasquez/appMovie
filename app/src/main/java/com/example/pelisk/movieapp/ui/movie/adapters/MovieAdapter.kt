package com.example.pelisk.movieapp.ui.movie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pelisk.databinding.MovieItemBinding
import com.example.pelisk.movieapp.core.BaseViewHolder
import com.example.pelisk.movieapp.data.model.Movie

class MovieAdapter(
    private val movieList: List<Movie>,
    private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        //se esta usando ViewBinding
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onMovieClick(movieList[position])

        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MoviesViewHolder -> holder.bind(movieList[position])
        }
    }

    override fun getItemCount(): Int = movieList.size

    //la palabra inner significa que esta clase interna forma parte de la clase que la contiene, es decir que cuando la instancia de MovieAdapter muera,tambien muere la instancia de la inner class
    //si no se le coloca el inner, y se cree instancias tanto de la clase interna y la clase que la contiene, al morir la instancia de la clase que lo contiene puede que la instancia de la clase interna no muera en memoria,
    //si se hace de ese modo (sin el inner) se tendra un alojamiento de memoria innecesario para un objeto que no se va a estar usando.
    private inner class MoviesViewHolder(
        val binding: MovieItemBinding,
        val contex: Context
    ) : BaseViewHolder<Movie>(binding.root) {


        override fun bind(item: Movie) {
            Glide.with(contex).load("https://image.tmdb.org/t/p/w500/" + item.poster_path)
                .centerCrop().into(binding.imgMovie)
        }

    }
}