package com.alerdoci.marvelsuperheroes.app.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen(route = "onboarding_screen")
    object Home : Screen(route = "home_screen")
}