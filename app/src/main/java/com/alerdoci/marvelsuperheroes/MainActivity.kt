package com.alerdoci.marvelsuperheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.alerdoci.marvelsuperheroes.app.navigation.SetupNavGraph
import com.alerdoci.marvelsuperheroes.app.screens.splash.viewmodel.SplashViewModel
import com.alerdoci.marvelsuperheroes.app.theme.MarvelSuperHeroesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                !splashViewModel.isLoading.value
            }
        }
        setContent {
            MarvelSuperHeroesTheme {
                // A surface container using the 'background' color from the theme
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    SetupNavGraph(navController = navController, startDestination = screen)
                }
            }
        }
    }
}
