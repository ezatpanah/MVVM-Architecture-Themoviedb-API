package com.ezatpanah.themoviedb_api_mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.themoviedb_api_mvvm.repository.ApiRepository
import com.ezatpanah.themoviedb_api_mvvm.response.CommonMoviesListResponse
import com.ezatpanah.themoviedb_api_mvvm.response.GenresListResponse
import com.ezatpanah.themoviedb_api_mvvm.response.UpcomingMoviesListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    val upcomingMoviesList = MutableLiveData<UpcomingMoviesListResponse>()
    val genreList = MutableLiveData<GenresListResponse>()
    val popularMovieList = MutableLiveData<CommonMoviesListResponse>()
    val genreMoviesList = MutableLiveData<CommonMoviesListResponse>()
    val searchMovieList = MutableLiveData<CommonMoviesListResponse>()
    val emptyList = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun loadUpcomingMoviesList() = viewModelScope.launch {
        val response = apiRepository.getUpcomingMoviesList(1)
        if (response.isSuccessful) {
            upcomingMoviesList.postValue(response.body())
        }
    }

    fun loadGenreList() = viewModelScope.launch {
        val response = apiRepository.getGenres()
        if (response.isSuccessful) {
            genreList.postValue(response.body())
        }
    }

    fun loadPopularMoviesList() = viewModelScope.launch {
        loading.postValue(true)
        val response = apiRepository.getPopularMoviesList(1)
        if (response.isSuccessful) {
            popularMovieList.postValue(response.body())
        }
        loading.postValue(false)
    }

    fun loadSearchMovieList(name: String) = viewModelScope.launch {
        loading.postValue(true)
        val response = apiRepository.getSearchMoviesList(1, name)
        if (response.isSuccessful) {
            if (response.body()?.results!!.isNotEmpty()) {
                searchMovieList.postValue(response.body())
                emptyList.postValue(false)
            } else {
                emptyList.postValue(true)
            }
        }
        loading.postValue(false)

    }

    fun loadGenreMoviesList(with_genres :String) = viewModelScope.launch {
        loading.postValue(true)
        val response = apiRepository.getMoviesGenres(1,with_genres)
        if (response.isSuccessful) {
            genreMoviesList.postValue(response.body())
        }
        loading.postValue(false)
    }





}