package com.alerdoci.marvelsuperheroes.app.application

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.alerdoci.marvelsuperheroes.app.common.network.ConnectivityObserver
import com.alerdoci.marvelsuperheroes.app.common.network.NetworkConnectivityObserver
import com.alerdoci.marvelsuperheroes.app.common.utils.ThemeMode
import com.alerdoci.marvelsuperheroes.app.navigation.SetupNavGraph
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.HomeViewModel
import com.alerdoci.marvelsuperheroes.app.screens.splash.viewmodel.SplashViewModel
import com.alerdoci.marvelsuperheroes.app.theme.MarvelSuperHeroesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel
    private lateinit var connectivityObserver: ConnectivityObserver
    lateinit var homeViewModel: HomeViewModel
    lateinit var superHeroViewModel: SuperHeroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //With Android activity 1.8.0 systemUiController is deprecated so we have to migrated to enableEdgeToEdge()
        // Example: https://github.com/android/nowinandroid/pull/817/files

        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        superHeroViewModel = ViewModelProvider(this)[SuperHeroViewModel::class.java]

        when (homeViewModel.getThemeValue()) {
            ThemeMode.Auto.ordinal -> homeViewModel.setTheme(ThemeMode.Auto)
            ThemeMode.Dark.ordinal -> homeViewModel.setTheme(ThemeMode.Dark)
            ThemeMode.Light.ordinal -> homeViewModel.setTheme(ThemeMode.Light)
        }

        installSplashScreen().apply {
            Thread.sleep(2500)
            this.setKeepOnScreenCondition {
                !splashViewModel.isLoading.value
            }
        }
        setContent {
            MarvelSuperHeroesTheme(homeViewModel = homeViewModel) {
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()

                val status by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Available
                )

                var previousStatus by remember { mutableStateOf(status) }

                if (status != previousStatus) {
                    previousStatus = status
                    val networkStatusText = "Network status: $status"
                    Toast.makeText(applicationContext, networkStatusText, Toast.LENGTH_SHORT).show()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    SetupNavGraph(
                        navController = navController,
                        startDestination = screen,
                        homeViewModel = homeViewModel,
                        superHeroViewModel = superHeroViewModel
                    )
                }
            }
        }
    }
}
