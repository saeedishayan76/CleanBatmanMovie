package com.shayan.composeBatmanMovie.domain.repository.usecase

import com.shayan.composeBatmanMovie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository){

    operator fun invoke () = movieRepository.getAllMovies()
    operator fun invoke (id: String) = movieRepository.getMovieDetail(id)
}