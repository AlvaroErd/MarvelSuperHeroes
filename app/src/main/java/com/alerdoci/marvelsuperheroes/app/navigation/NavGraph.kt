package com.alerdoci.marvelsuperheroes.app.navigation

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alerdoci.marvelsuperheroes.app.screens.home.composable.HomeScreen
import com.alerdoci.marvelsuperheroes.app.screens.superhero.SuperheroActivity

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Home
        composable(route = Screen.Home.route) {
            StartAnimation(
                enterTransition = slideInHorizontally() + fadeIn(),
                exitTransition = slideOutHorizontally() + fadeOut()
            ) {
                val appContext = LocalContext.current
                HomeScreen(
                    navController,
                    onItemClick = { superHeroClickedId ->
                        appContext.startActivity(
                            Intent(appContext, SuperheroActivity::class.java).apply {
                                putExtra("superHeroId", superHeroClickedId)
                            }
                        )
                    }
                )
            }
        }
    }
}

