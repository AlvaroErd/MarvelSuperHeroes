package com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
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
) : ViewModel() {

    private val _currentSuperHero by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val currentSuperHero: StateFlow<ResourceState<*>>
        get() = _currentSuperHero

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
        }
    }
}