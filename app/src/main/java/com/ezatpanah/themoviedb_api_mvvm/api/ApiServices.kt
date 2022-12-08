package com.ezatpanah.themoviedb_api_mvvm.api

import com.ezatpanah.themoviedb_api_mvvm.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    //    https://api.themoviedb.org/3/movie/upcoming?api_key=***&page=1
    //    https://api.themoviedb.org/3/movie/popular?api_key=***
    //    https://api.themoviedb.org/3/genre/movie/list?api_key=***
    //    https://api.themoviedb.org/3/search/movie?api_key=***&query=MovieName&page=1
    //    https://api.themoviedb.org/3/movie/{movie_id}?api_key=***
    //    https://api.themoviedb.org/3/movie/{movie_id}/credits?api_key=***



    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesList(@Query("page") page: Int): Response<UpcomingMoviesListResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresListResponse>

    @GET("discover/movie")
    suspend fun getMoviesGenres(@Query("page") page: Int,@Query("with_genres") with_genres: String): Response<CommonMoviesListResponse>

    @GET("movie/popular")
    suspend fun getPopularMoviesList(@Query("page") page: Int ): Response<CommonMoviesListResponse>

    @GET("search/movie")
    suspend fun getSearchMoviesList(@Query("page") page: Int,@Query("query") query: String): Response<CommonMoviesListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<DetailsMovieResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") id: Int): Response<CreditsLisResponse>

}