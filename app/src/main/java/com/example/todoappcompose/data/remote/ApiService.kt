package com.shayan.composeBatmanMovie.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?apikey=2c636cdc&s=batman")
    suspend fun getAllMovies(): MovieDto

    @GET("?apikey=2c636cdc")
    suspend fun getMovieDetail(
        @Query("i") id: String
    ): MovieDetailDto
}