package com.shayan.composeBatmanMovie.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shayan.composeBatmanMovie.data.remote.MovieDetailDto
import com.shayan.composeBatmanMovie.domain.repository.model.Movie
import com.shayan.composeBatmanMovie.domain.repository.usecase.MovieUseCase
import com.shayan.composeBatmanMovie.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _movieStateFlow = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val movieStateFlow = _movieStateFlow.asStateFlow()

    private val _movieDetailStateFlow = MutableStateFlow<Resource<MovieDetailDto>?>(null)
    val movieDetailStateFlow = _movieDetailStateFlow.asStateFlow()

    val selectedMovie = MutableStateFlow<Movie?>(null)

    var getAllMovieState = false


    fun fetchMovies() {
        movieUseCase.invoke().onEach {
            _movieStateFlow.value = it
        }.launchIn(viewModelScope)
    }

    fun fetchMovieDetail(id: String) {
        movieUseCase(id).onEach {
            _movieDetailStateFlow.value = it
        }.launchIn(viewModelScope)
    }
}