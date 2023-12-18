package com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.navigation.SUPERHERO_ID_KEY
import com.alerdoci.marvelsuperheroes.domain.usecases.app.GetMarvelSuperHeroComicsUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.app.GetMarvelSuperHeroUseCase
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InvalidObjectException
import javax.inject.Inject

@HiltViewModel
class SuperHeroViewModel @Inject constructor(
    private val getMarvelSuperHeroUseCase: GetMarvelSuperHeroUseCase,
    private val getMarvelSuperHeroComicsUseCase: GetMarvelSuperHeroComicsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val superheroId = savedStateHandle.get<Int>(SUPERHERO_ID_KEY)

    private val _currentSuperHero by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val currentSuperHero: StateFlow<ResourceState<*>>
        get() = _currentSuperHero

    val loadSuperHeroComic: StateFlow<PagingData<ModelComicsResult>> =
        superheroId?.let { id ->
            getMarvelSuperHeroComicsUseCase(id)
                .cachedIn(viewModelScope)
        }?.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
            ?: MutableStateFlow(PagingData.empty())

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
