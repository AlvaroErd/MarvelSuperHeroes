package com.alerdoci.marvelsuperheroes.app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alerdoci.marvelsuperheroes.app.screens.home.composable.HomeScreen
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.SettingsViewModel
import com.alerdoci.marvelsuperheroes.app.screens.onboarding.composables.OnBoardingScreen
import com.alerdoci.marvelsuperheroes.app.screens.superhero.composable.SuperheroScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    settingsViewModel: SettingsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // Onboarding
        composable(route = Screen.Onboarding.route) {
            OnBoardingScreen(navController = navController)
        }

        // Home
        composable(route = Screen.Home.route) {
            StartAnimation(
                enterTransition = slideInHorizontally() + fadeIn(),
                exitTransition = slideOutHorizontally() + fadeOut()
            ) {
                val searchQuery: String = ""

                HomeScreen(
                    navController,
                    searchQuery = searchQuery,
                    settingsViewModel = settingsViewModel
                )
            }
        }

        // SuperHero detail
        composable(
            route = Screen.Superhero.route,
            arguments = listOf(navArgument(SUPERHERO_ID_KEY) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            StartAnimation(
                enterTransition = slideInHorizontally() + fadeIn(),
                exitTransition = slideOutHorizontally() + fadeOut()
            ) {
                val superheroId = backStackEntry.arguments!!.getInt(SUPERHERO_ID_KEY)
                val superheroScreenSavedStateHandle = backStackEntry.savedStateHandle
                superheroScreenSavedStateHandle.set(SUPERHERO_ID_KEY, superheroId)

                SuperheroScreen(
                    superheroId = superheroId
                )
            }
        }
    }
}
