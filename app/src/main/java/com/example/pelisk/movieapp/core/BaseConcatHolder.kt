package com.example.pelisk.movieapp.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseConcatHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(adapter:T)
}