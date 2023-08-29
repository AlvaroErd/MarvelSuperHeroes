package com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.utils.ThemeMode
import com.alerdoci.marvelsuperheroes.data.features.onboarding.cache.settings.DataStoreRepository
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroComicsUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InvalidObjectException
import javax.inject.Inject

@HiltViewModel
class SuperHeroViewModel @Inject constructor(
    private val getMarvelSuperHeroUseCase: GetMarvelSuperHeroUseCase,
    private val getMarvelSuperHeroComicsUseCase: GetMarvelSuperHeroComicsUseCase,
    val datastore : DataStoreRepository
) : ViewModel() {

    private val _currentSuperHero by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val currentSuperHero: StateFlow<ResourceState<*>>
        get() = _currentSuperHero

    private val _currentSuperHeroComic by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val currentSuperHeroComic: StateFlow<ResourceState<*>>
        get() = _currentSuperHeroComic

    fun loadSuperHero(superHeroId: Int) {
        _currentSuperHero.update { ResourceState.Loading("") }
        viewModelScope.launch(Dispatchers.IO) {

            getMarvelSuperHeroUseCase(superHeroId = superHeroId).collectLatest { superHero ->
                _currentSuperHero.update {
                    if (superHero.isNotEmpty())
                        ResourceState.Success(superHero)
                    else
                        ResourceState.Error(InvalidObjectException("SuperHero not found :("))
                }
            }

            getMarvelSuperHeroComicsUseCase(superHeroId = superHeroId).collectLatest { superHeroComics ->
                _currentSuperHeroComic.update {
                    if (superHeroComics.isNotEmpty())
                        ResourceState.Success(superHeroComics)
                    else
                        ResourceState.Error(InvalidObjectException("SuperHeroComic not found :("))
                }
            }
        }
    }

    fun calculateCurrentTheme(): ThemeMode {
        val savedTheme = datastore.getInt(DataStoreRepository.APP_THEME_INT, ThemeMode.Auto.ordinal)
        return ThemeMode.values()[savedTheme]
    }

}