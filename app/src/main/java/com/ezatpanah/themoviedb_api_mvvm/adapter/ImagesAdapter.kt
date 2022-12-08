package com.ezatpanah.themoviedb_api_mvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ezatpanah.themoviedb_api_mvvm.R
import com.ezatpanah.themoviedb_api_mvvm.databinding.ItemDetailImagesBinding
import com.ezatpanah.themoviedb_api_mvvm.response.CreditsLisResponse
import com.ezatpanah.themoviedb_api_mvvm.utils.Constants
import javax.inject.Inject

class ImagesAdapter @Inject constructor() : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private lateinit var binding: ItemDetailImagesBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDetailImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(item: CreditsLisResponse.Cast) {
            binding.apply {
                tvName.text=item.name
                val moviePosterURL = Constants.POSTER_BASE_URL + item?.profilePath
                itemImages.load(moviePosterURL) {
                    placeholder(R.drawable.ic_baseline_account_circle_24)
                    crossfade(true)
                    crossfade(800)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CreditsLisResponse.Cast>() {
        override fun areItemsTheSame(oldItem: CreditsLisResponse.Cast, newItem: CreditsLisResponse.Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CreditsLisResponse.Cast, newItem: CreditsLisResponse.Cast): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}