package com.shayan.composeBatmanMovie.data.remote

data class MovieDto(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)

