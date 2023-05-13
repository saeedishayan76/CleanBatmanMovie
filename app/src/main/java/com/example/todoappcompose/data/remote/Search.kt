package com.shayan.composeBatmanMovie.data.remote

import com.shayan.composeBatmanMovie.domain.repository.model.Movie

data class Search(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
)

fun Search.toMovie(): Movie {
    return Movie(title = Title, image = Poster, year = Year, id = imdbID)
}