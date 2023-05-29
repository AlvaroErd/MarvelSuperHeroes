package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroesPagingUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMarvelSuperHeroesUseCase: GetMarvelSuperHeroesUseCase,
    private val getMarvelSuperHeroesPagingUseCase: GetMarvelSuperHeroesPagingUseCase
) : ViewModel() {

    private val _superHeroes by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val superHeroes: StateFlow<ResourceState<*>>
        get() = _superHeroes

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadSuperHeroes()
        }
    }

    private suspend fun loadSuperHeroes() {
        if (_superHeroes.compareAndSet(ResourceState.Idle, ResourceState.Loading(""))) {
            getMarvelSuperHeroesUseCase()
                .onCompletion { throwable ->
                    if (throwable == null) {
                        delay(1500)
                        _superHeroes.update { ResourceState.Idle }
                    } else
                        _superHeroes.update { ResourceState.Error(throwable) }
                }
                .collectLatest { superHeroesList ->
                    _superHeroes.update { ResourceState.Success(superHeroesList) }
                }
        }
    }

    fun getMargelSuperHeroesPager(marvelSuperheroes: ModelResult): Flow<PagingData<ModelResult>> =
        if (marvelSuperheroes == null)
            flowOf()
        else
            getMarvelSuperHeroesPagingUseCase(marvelSuperheroes)

}
//
////Mock
//val marvelSuperHero1 = ModelResult(
//comics = "",
//description = "",
//events = "",
//id = "",
//modified = "",
//name = "",
//resourceURI = "",
//series = "",
//stories = "",
//thumbnail = "",
//urls = "",
//)
//
//var marvelSuperHeroes = listOf(
//    marvelSuperHero1,
//    marvelSuperHero2,
//    marvelSuperHero3,
//)
