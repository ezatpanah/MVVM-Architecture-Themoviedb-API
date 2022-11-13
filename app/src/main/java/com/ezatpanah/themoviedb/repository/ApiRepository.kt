package com.ezatpanah.themoviedb.repository


import com.ezatpanah.themoviedb.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices
) {
    //Api
    suspend fun getUpcomingMoviesList(page: Int) = apiServices.getUpcomingMoviesList(page)
    suspend fun getGenres()=apiServices.getGenres()
    suspend fun getMoviesGenres(page: Int ,with_genres :String)=apiServices.getMoviesGenres(page,with_genres)
    suspend fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)
    suspend fun getSearchMoviesList(page: Int,query: String) = apiServices.getSearchMoviesList(page,query)
    suspend fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)

}