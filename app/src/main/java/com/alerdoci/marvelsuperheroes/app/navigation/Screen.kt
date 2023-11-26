package com.alerdoci.marvelsuperheroes.app.navigation

const val SUPERHERO_ID_KEY = "superHeroId"
sealed class Screen(val route: String) {
    object Onboarding : Screen(route = "onboarding_screen")
    object Home : Screen(route = "home_screen")
    object Superhero : Screen(route = "superhero_detail/{$SUPERHERO_ID_KEY}") {
        fun navigateWithId(id:Int): String {
            return this.route.replace(oldValue = "{$SUPERHERO_ID_KEY}", newValue = id.toString())
        }
    }
}
