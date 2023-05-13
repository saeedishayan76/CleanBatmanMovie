package com.shayan.composeBatmanMovie.data.repoImpl

import android.util.Log
import com.shayan.composeBatmanMovie.common.Util
import com.shayan.composeBatmanMovie.data.remote.ApiService
import com.shayan.composeBatmanMovie.data.remote.MovieDetailDto
import com.shayan.composeBatmanMovie.data.remote.toMovie
import com.shayan.composeBatmanMovie.domain.repository.model.Movie
import com.shayan.composeBatmanMovie.domain.repository.MovieRepository
import com.shayan.composeBatmanMovie.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MovieRepository {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)

        emit(Util.wrapTryCatch {
            apiService.getAllMovies().Search.map { it.toMovie() }
        })

    }

    override fun getMovieDetail(id: String): Flow<Resource<MovieDetailDto>> = flow {
        emit(Resource.Loading)

        emit(Util.wrapTryCatch {
            apiService.getMovieDetail(id)
        })
    }
}