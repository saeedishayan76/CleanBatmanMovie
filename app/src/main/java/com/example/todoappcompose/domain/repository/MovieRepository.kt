package com.shayan.composeBatmanMovie.domain.repository

import com.shayan.composeBatmanMovie.data.remote.MovieDetailDto
import com.shayan.composeBatmanMovie.domain.repository.model.Movie
import com.shayan.composeBatmanMovie.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getMovieDetail(id: String): Flow<Resource<MovieDetailDto>>
}