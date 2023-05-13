package com.shayan.composeBatmanMovie.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.shayan.composeBatmanMovie.R
import com.shayan.composeBatmanMovie.constant.NavItem
import com.shayan.composeBatmanMovie.domain.repository.model.Movie
import com.shayan.composeBatmanMovie.screens.MovieViewModel
import com.shayan.composeBatmanMovie.vo.Resource
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current

    // Set the status bar color


    val data = movieViewModel.movieStateFlow.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        if (!movieViewModel.getAllMovieState) {

            movieViewModel.fetchMovies()
            movieViewModel.getAllMovieState = true
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        data.let {
            when (it) {
                is Resource.Loading -> {
                    LinearProgressIndicator()
                }

                is Resource.Success -> {
                    Column() {
                        Text(
                            text = "Batman Movies", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.mont_bold)),
                            modifier = Modifier.padding(16.dp)
                        )
                        showDataList(it.data as ArrayList<Movie>, movieViewModel, navController)
                    }


                }

                is Resource.Error -> {
                    Log.i("get movie", "onCreate: error ${it.message}")

                }
            }

        }
    }


}

@Composable
fun showDataList(
    data: ArrayList<Movie>, movieViewModel: MovieViewModel,
    navController: NavHostController
) {


    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(data, key = {
            it.id
        }) { movie ->
            MovieItem(movie = movie) { selectedMovie ->
                movieViewModel.selectedMovie.value = selectedMovie
                navController.navigate(NavItem.Detail.route+"/${selectedMovie.id}")
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier, onMovieSelected: (Movie) -> Unit) {

    Card(
        modifier = modifier
            .clickable {
                onMovieSelected.invoke(movie)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            Color(0xFFF1EEEE)
        ),
    ) {


        val boldFont = Font(R.font.mont_bold, weight = FontWeight.Normal)
        val regularFont = Font(R.font.mont, weight = FontWeight.Normal)
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (imgMovie, tvTitle, tvMovie, imgStar, rowScore, tvDescription) = createRefs()

            Image(painter = rememberAsyncImagePainter(model = movie.image),
                contentDescription = "movie image",
                contentScale = ContentScale.Crop,
                modifier =
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .constrainAs(imgMovie) {
                        width = Dimension.value(70.dp)
                        height = Dimension.value(100.dp)
                        top.linkTo(parent.top, 32.dp)
                        start.linkTo(parent.start, 16.dp)
                        bottom.linkTo(parent.bottom, 32.dp)

                    })
            Text(
                text = movie.title,
                maxLines = 2,
                fontSize = 18.sp,
                fontFamily = FontFamily(boldFont),
                color = Color(0xFF252322),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(tvTitle) {
                    start.linkTo(imgMovie.end, 16.dp)
                    top.linkTo(imgMovie.top)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                })
            Text(
                text = "Movie",
                color = Color(0xFF363230),
                fontFamily = FontFamily(regularFont),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(tvMovie) {
                    bottom.linkTo(imgMovie.bottom, 8.dp)
                    start.linkTo(imgMovie.end, 16.dp)
                })

            Text (
                text = "imdId : ${movie.id}",
                color = Color(0xFF363230),
                fontFamily = FontFamily(boldFont),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(tvDescription) {
                   top.linkTo(tvTitle.bottom, 16.dp)
                    start.linkTo(imgMovie.end, 16.dp)
                }
            )

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(rowScore) {
                    end.linkTo(parent.end)
                    bottom.linkTo(imgMovie.bottom)
                }) {

                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "star icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "${(2..9).random().toDouble()}",
                    color = Color(0xFF363230),
                    fontSize = 13.sp,
                    fontFamily = FontFamily(regularFont),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }


        }


    }
}
