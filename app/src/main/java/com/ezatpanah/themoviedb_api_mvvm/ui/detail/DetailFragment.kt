package com.ezatpanah.themoviedb_api_mvvm.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ezatpanah.themoviedb_api_mvvm.R
import com.ezatpanah.themoviedb_api_mvvm.adapter.ImagesAdapter
import com.ezatpanah.themoviedb_api_mvvm.databinding.FragmentDetailBinding
import com.ezatpanah.themoviedb_api_mvvm.db.MoviesEntity
import com.ezatpanah.themoviedb_api_mvvm.utils.Constants
import com.ezatpanah.themoviedb_api_mvvm.utils.initRecycler
import com.ezatpanah.themoviedb_api_mvvm.utils.showInvisible
import com.ezatpanah.themoviedb_api_mvvm.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private var movieId = 0
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var entity: MoviesEntity

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId > 0) {
            detailsViewModel.loadDetailsMovie(movieId)
            detailsViewModel.loadCreditsMovie(movieId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            detailsViewModel.detailsMovie.observe(viewLifecycleOwner) { response ->
                val moviePosterURL = Constants.POSTER_BASE_URL + response.posterPath
                posterBigImg.load(moviePosterURL)
                posterNormalImg.load(moviePosterURL) {
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = response.title
                movieRateTxt.text = response.voteAverage.toString()
                movieTimeTxt.text = response.runtime.toString()
                movieDateTxt.text = response.releaseDate
                movieSummaryInfo.text = response.overview

                favImg.setOnClickListener {
                    entity.id = movieId
                    entity.poster = response.posterPath
                    entity.lang = response.originalLanguage
                    entity.title = response.title
                    entity.rate = response.voteAverage.toString()
                    entity.year = response.releaseDate
                    detailsViewModel.favoriteMovie(movieId, entity)
                }

            }


            detailsViewModel.creditsMovie.observe(viewLifecycleOwner){ response ->
                imagesAdapter.differ.submitList(response.cast)
                imagesRecyclerView.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), imagesAdapter
                )

            }


            detailsViewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    detailLoading.showInvisible(true)
                    detailScrollView.showInvisible(false)
                } else {
                    detailLoading.showInvisible(false)
                    detailScrollView.showInvisible(true)
                }
            }

            lifecycleScope.launchWhenCreated {
                if (detailsViewModel.existMovie(movieId)) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                }
            }

            detailsViewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                }
            }

            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}