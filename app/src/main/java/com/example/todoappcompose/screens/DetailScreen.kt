package com.shayan.composeBatmanMovie.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.shayan.composeBatmanMovie.vo.Resource

private const val TAG = "DetailScreen"

@Composable
fun DetailScreen(movieViewModel: MovieViewModel, id: String?) {

    Log.i(TAG, "DetailScreen: id is ${id}")
    val movieDetail = movieViewModel.movieDetailStateFlow.collectAsState().value

    LaunchedEffect(key1 = Unit, ) {

        movieViewModel.fetchMovieDetail(id!!)
    }

    movieDetail?.let {
        when(it) {
            is Resource.Loading -> {
                Log.i(TAG, "DetailScreen: loading")
            }
            is Resource.Success -> {
                Log.i(TAG, "DetailScreen: success ${it.data}")

            }
            is Resource.Error -> {
                Log.i(TAG, "DetailScreen: error ${it.message}")

            }
        }
    }


}