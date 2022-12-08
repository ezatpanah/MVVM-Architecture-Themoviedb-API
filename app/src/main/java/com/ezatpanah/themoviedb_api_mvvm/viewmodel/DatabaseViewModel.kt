package com.ezatpanah.themoviedb_api_mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.themoviedb_api_mvvm.db.MoviesEntity
import com.ezatpanah.themoviedb_api_mvvm.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    val favoriteMovieList = MutableLiveData<MutableList<MoviesEntity>>()
    val emptyList = MutableLiveData<Boolean>()

    fun loadFavoriteMovieList() = viewModelScope.launch {
        val list = repository.getAllFavoriteList()
        if (list.isNotEmpty()) {
            favoriteMovieList.postValue(list)
            emptyList.postValue(false)
        } else {
            emptyList.postValue(true)
        }
    }


}