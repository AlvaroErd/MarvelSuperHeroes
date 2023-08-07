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
import androidx.navigation.compose.rememberNavController
import com.alerdoci.marvelsuperheroes.app.common.network.ConnectivityObserver
import com.alerdoci.marvelsuperheroes.app.common.network.NetworkConnectivityObserver
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
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        installSplashScreen().apply {
            Thread.sleep(2500)
            this.setKeepOnScreenCondition {
                !splashViewModel.isLoading.value
            }
        }
        setContent {
            MarvelSuperHeroesTheme {
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
                    SetupNavGraph(navController = navController, startDestination = screen)
                }
            }
        }
    }
}
