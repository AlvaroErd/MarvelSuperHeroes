package com.alerdoci.marvelsuperheroes.app.application

import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.alerdoci.marvelsuperheroes.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelSuperHeroesApp : Application() {

    //Section handle error crash with a library provided by Ereza
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate() {
        super.onCreate()
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            //if we want to pause this library, change to false
            .enabled(true)
            .showErrorDetails(true)
            .showRestartButton(true)
            .trackActivities(true)
            .errorDrawable(R.drawable.error_awkward_gif)
            .restartActivity(MainActivity::class.java).apply()
    }

//    if we want to crash some screen
//    throw RuntimeException("Boom!")

}