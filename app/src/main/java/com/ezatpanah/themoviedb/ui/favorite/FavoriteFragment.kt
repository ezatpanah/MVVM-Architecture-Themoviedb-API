package com.ezatpanah.themoviedb.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.themoviedb.adapter.FavoriteMoviesAdapter
import com.ezatpanah.themoviedb.databinding.FragmentFavoriteBinding
import com.ezatpanah.themoviedb.utils.initRecycler
import com.ezatpanah.themoviedb.utils.showInvisible
import com.ezatpanah.themoviedb.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            databaseViewModel.loadFavoriteMovieList()
            databaseViewModel.favoriteMovieList.observe(viewLifecycleOwner){
                favoriteMoviesAdapter.bind(it)
                favoriteRecycler.initRecycler(LinearLayoutManager(requireContext()),favoriteMoviesAdapter)
            }

            favoriteMoviesAdapter.setonItemClickListener {
                val direction = FavoriteFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }

            databaseViewModel.emptyList.observe(viewLifecycleOwner){
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
            }
        }
    }

}