package com.shayan.composeBatmanMovie.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shayan.composeBatmanMovie.constant.NavItem
import com.shayan.composeBatmanMovie.screens.DetailScreen
import com.shayan.composeBatmanMovie.screens.MovieViewModel
import com.shayan.composeBatmanMovie.ui.theme.TodoAppComposeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoAppComposeTheme {


                val navController = rememberNavController()
                val sharedCoinViewModel: MovieViewModel = hiltViewModel()
                NavHost(navController = navController, startDestination = NavItem.Home.route) {
                    composable(NavItem.Home.route) {
                        HomeScreen(movieViewModel = sharedCoinViewModel, modifier = Modifier.fillMaxSize(), navController = navController)
                    }
                    composable(NavItem.Detail.route + "/{id}", arguments = listOf(navArgument("id"){
                        type = NavType.StringType
                    })) {
                        val arg = it.arguments?.getString("id")
                        DetailScreen(sharedCoinViewModel, arg)
                    }
                }


            }
        }
    }
}

