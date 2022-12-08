package com.ezatpanah.themoviedb_api_mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.ezatpanah.themoviedb_api_mvvm.databinding.ItemMoviesUpcomingBinding
import com.ezatpanah.themoviedb_api_mvvm.response.UpcomingMoviesListResponse.*
import com.ezatpanah.themoviedb_api_mvvm.utils.Constants.POSTER_BASE_URL
import javax.inject.Inject

class UpcomingMoviesAdapter @Inject constructor() : RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>() {
    private lateinit var binding: ItemMoviesUpcomingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemMoviesUpcomingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = if (differ.currentList.size>5) 5 else differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){
        fun setData(item : Result){
            binding.apply {
                movieNameTxt.text =item.title
                movieInfoTxt.text="${item.voteAverage}"
                val moviePosterURL = POSTER_BASE_URL + item?.posterPath
                moviePosterImg.load(moviePosterURL){
                    crossfade(true)
                    crossfade(800)
                    scale(Scale.FIT)
                }
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

}