package com.ezatpanah.themoviedb_api_mvvm.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.themoviedb_api_mvvm.adapter.CommonMoviesAdapter
import com.ezatpanah.themoviedb_api_mvvm.databinding.FragmentSearchBinding
import com.ezatpanah.themoviedb_api_mvvm.utils.initRecycler
import com.ezatpanah.themoviedb_api_mvvm.utils.showInvisible
import com.ezatpanah.themoviedb_api_mvvm.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var commonMoviesAdapter: CommonMoviesAdapter

    private val mainViewModel: ApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            searchEdt.addTextChangedListener {
                val search = it.toString()
                if (search.isNotEmpty()) {
                    mainViewModel.loadSearchMovieList(search)
                }
            }
            mainViewModel.searchMovieList.observe(viewLifecycleOwner) {
                commonMoviesAdapter.bind(it.results)
                moviesRecycler.initRecycler(LinearLayoutManager(requireContext()), commonMoviesAdapter)
            }

            commonMoviesAdapter.setonItemClickListener {
                val direction = SearchFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }

            mainViewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    searchLoading.showInvisible(true)
                } else {
                    searchLoading.showInvisible(false)
                }
            }

            mainViewModel.emptyList.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    moviesRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    moviesRecycler.showInvisible(true)
                }
            }
        }
    }
}