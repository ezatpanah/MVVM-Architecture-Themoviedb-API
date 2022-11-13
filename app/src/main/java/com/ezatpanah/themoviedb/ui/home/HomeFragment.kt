package com.ezatpanah.themoviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.ezatpanah.themoviedb.adapter.CommonMoviesAdapter
import com.ezatpanah.themoviedb.adapter.GenreMoviesAdapter
import com.ezatpanah.themoviedb.adapter.UpcomingMoviesAdapter
import com.ezatpanah.themoviedb.databinding.FragmentHomeBinding
import com.ezatpanah.themoviedb.utils.initRecycler
import com.ezatpanah.themoviedb.utils.showInvisible
import com.ezatpanah.themoviedb.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val apiViewModel: ApiViewModel by viewModels()
    private val pagerHelper: PagerSnapHelper by lazy { PagerSnapHelper() }

    @Inject
    lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter

    @Inject
    lateinit var genreMoviesAdapter: GenreMoviesAdapter

    @Inject
    lateinit var commonMoviesAdapter: CommonMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiViewModel.loadUpcomingMoviesList()
        apiViewModel.loadGenreList()
        apiViewModel.loadPopularMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            apiViewModel.upcomingMoviesList.observe(viewLifecycleOwner) {
                // UpcomingMovies
                upcomingMoviesAdapter.differ.submitList(it.results)
                topMoviesRecycler.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    upcomingMoviesAdapter
                )
                pagerHelper.attachToRecyclerView(topMoviesRecycler)
                topMoviesIndicator.attachToRecyclerView(topMoviesRecycler, pagerHelper)
            }
            // Genre List
            apiViewModel.genreList.observe(viewLifecycleOwner) {
                genreMoviesAdapter.differ.submitList(it.genres)
                genresRecycler.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), genreMoviesAdapter
                )
            }

            //Genres Movies List
            genreMoviesAdapter.setonItemClickListener {
                apiViewModel.loadGenreMoviesList(it.id.toString())
                apiViewModel.genreMoviesList.observe(viewLifecycleOwner) {
                    commonMoviesAdapter.bind(it.results)
                    lastMoviesRecycler.initRecycler(
                        LinearLayoutManager(requireContext()), commonMoviesAdapter
                    )
                }
                lastMoviesTitle.text =it.name
            }

            //Popular Movies List
            apiViewModel.popularMovieList.observe(viewLifecycleOwner) {
                commonMoviesAdapter.bind(it.results)
                lastMoviesRecycler.initRecycler(
                    LinearLayoutManager(requireContext()), commonMoviesAdapter
                )
            }

            commonMoviesAdapter.setonItemClickListener {
                val direction = HomeFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }



            //Loading
            apiViewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    moviesLoading.showInvisible(true)
                    //moviesScrollLay.showInvisible(false)
                    lastMoviesLay.showInvisible(false)
                } else {
                    moviesLoading.showInvisible(false)
                    lastMoviesLay.showInvisible(true)
                }
            }
        }
    }
}