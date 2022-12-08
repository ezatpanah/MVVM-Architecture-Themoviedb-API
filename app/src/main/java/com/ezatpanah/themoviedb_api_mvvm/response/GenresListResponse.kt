package com.ezatpanah.themoviedb_api_mvvm.response


import com.google.gson.annotations.SerializedName

data class GenresListResponse(
    @SerializedName("genres")
    val genres: List<Genre?>?
) {
    data class Genre(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?
    )
}