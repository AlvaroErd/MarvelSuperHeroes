package com.alerdoci.marvelsuperheroes.app.screens.superhero

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.application.MainActivity
import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.getActivity
import com.alerdoci.marvelsuperheroes.app.common.utils.ThemeMode
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.HomeViewModel
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel
import com.alerdoci.marvelsuperheroes.databinding.ActivitySuperheroBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SuperheroActivity : AppCompatActivity() {

//    lateinit var superHeroViewModel: SuperHeroViewModel
    private var binding: ActivitySuperheroBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivitySuperheroBinding.inflate(this.layoutInflater)
        setContentView(this.binding!!.root)

//        superHeroViewModel = ViewModelProvider(this)[SuperHeroViewModel::class.java]
//
//        when (superHeroViewModel.calculateCurrentTheme()) {
//
//                ThemeMode.Dark ->  enableDarkMode()
//                ThemeMode.Light -> disableDarkMode()
//                else ->enableDarkMode()
//
//        }

        val superHeroId = this.intent.getIntExtra("superHeroId", 0)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SuperHeroFragment.newInstance(superHeroId))
                .commitNow()
        }

    }
//    fun enableDarkMode() {
//            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
//            delegate.applyDayNight()
//    }
//
//    fun disableDarkMode() {
//        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
//        delegate.applyDayNight()
//    }
}
