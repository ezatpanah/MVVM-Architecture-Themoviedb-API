package com.ezatpanah.themoviedb.response


import com.google.gson.annotations.SerializedName

data class UpcomingMoviesListResponse(
    @SerializedName("dates")
    val dates: Dates?,
    @SerializedName("page")
    val page: Int?, // 1
    @SerializedName("results")
    val results: List<Result?>?,
    @SerializedName("total_pages")
    val totalPages: Int?, // 24
    @SerializedName("total_results")
    val totalResults: Int? // 467
) {
    data class Dates(
        @SerializedName("maximum")
        val maximum: String?, // 2022-11-23
        @SerializedName("minimum")
        val minimum: String? // 2022-11-02
    )

    data class Result(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /y5Z0WesTjvn59jP6yo459eUsbli.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int?>?,
        @SerializedName("id")
        val id: Int?, // 663712
        @SerializedName("original_language")
        val originalLanguage: String?, // en
        @SerializedName("original_title")
        val originalTitle: String?, // Terrifier 2
        @SerializedName("overview")
        val overview: String?, // After being resurrected by a sinister entity, Art the Clown returns to Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.
        @SerializedName("popularity")
        val popularity: Double?, // 4620.725
        @SerializedName("poster_path")
        val posterPath: String?, // /yw8NQyvbeNXoZO6v4SEXrgQ27Ll.jpg
        @SerializedName("release_date")
        val releaseDate: String?, // 2022-10-06
        @SerializedName("title")
        val title: String?, // Terrifier 2
        @SerializedName("video")
        val video: Boolean?, // false
        @SerializedName("vote_average")
        val voteAverage: Double?, // 7.5
        @SerializedName("vote_count")
        val voteCount: Int? // 128
    )
}