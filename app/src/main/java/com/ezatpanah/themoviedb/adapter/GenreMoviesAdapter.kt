package com.ezatpanah.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.themoviedb.databinding.ItemHomeGenreListBinding
import com.ezatpanah.themoviedb.response.CommonMoviesListResponse
import com.ezatpanah.themoviedb.response.GenresListResponse.Genre
import javax.inject.Inject

class GenreMoviesAdapter @Inject constructor() : RecyclerView.Adapter<GenreMoviesAdapter.ViewHolder>() {
    private lateinit var binding: ItemHomeGenreListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemHomeGenreListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int =differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){
        fun setData(item : Genre){
            binding.apply {
                nameTxt.text=item.name

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener : ((Genre) -> Unit)? = null
    fun setonItemClickListener(listener: (Genre) -> Unit) {
        onItemClickListener=listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Genre>(){
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

}