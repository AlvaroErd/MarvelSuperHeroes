package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroSearchedUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroesPagingUseCase
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesComics
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesEvents
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InvalidObjectException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMarvelSuperHeroesPagingUseCase: GetMarvelSuperHeroesPagingUseCase,
    private val getMarvelSuperHeroSearchedUseCase: GetMarvelSuperHeroSearchedUseCase,
) : ViewModel() {

    private val _superHeroes by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val superHeroes: StateFlow<ResourceState<*>>
        get() = _superHeroes

    private val _superHeroSearched by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val superHeroSearched: StateFlow<ResourceState<*>>
        get() = _superHeroSearched

    fun getMarvelSuperHeroesPager(): Flow<PagingData<SuperHeroesResult>> =
        getMarvelSuperHeroesPagingUseCase()
            .catch { exception ->
                _superHeroes.update { ResourceState.Error(exception) }
                Log.e("Error handling SuperHeroes Pager", exception.message ?: exception.toString())
            }
            .onStart {
                _superHeroes.update { ResourceState.Loading("") }
            }
            .map { pagingData ->
                delay(1000)
                _superHeroes.update { ResourceState.Success("") }
                pagingData
            }

    fun getSuperHeroSearched(nameSearched: String?) {
        _superHeroSearched.update { ResourceState.Loading("") }
        viewModelScope.launch(Dispatchers.IO) {

            getMarvelSuperHeroSearchedUseCase(nameSearched = nameSearched).collectLatest { superHero ->
                _superHeroSearched.update {
                    if (superHero.isNotEmpty())
                        ResourceState.Success(superHero)
                    else
                        ResourceState.Error(InvalidObjectException("SuperHero not found :("))
                }
            }
        }
    }
}

//Mock
val marvelSuperHeroMock1 = SuperHeroesResult(
    id = 1,
    name = "3-D Man",
    description = "Fabulas reformidans viverra invidunt errem vis vitae fastidii. Convenire odio ipsum mutat ligula sociosqu scripserit civibus dicit. Expetenda tortor bibendum instructior maiestatis.",
    comics = SuperHeroesComics(55),
    series = SuperHeroesSeries(42),
    events = SuperHeroesEvents(6),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
)

val marvelSuperHeroMock2 = SuperHeroesResult(
    id = 2,
    name = "A-Bomb (HAS)",
    description = "Te theophrastus idque pro hinc mediocritatem metus definiebas rutrum. Sed affert sapientem delicata vituperata at quaestio molestiae turpis gubergren. Neglegentur deserunt ferri dicam feugiat nisl expetendis dolore iuvaret.",
    comics = SuperHeroesComics(0),
    series = SuperHeroesSeries(1),
    events = SuperHeroesEvents(8),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg "
)

val marvelSuperHeroMock3 = SuperHeroesResult(
    id = 3,
    name = "A.I.M.",
    description = "Ante vocent suscipit disputationi persecuti noluisse. Dolorem congue dolores finibus ipsum option. Iusto aliquip ligula omittantur purus comprehensam platonem rhoncus.",
    comics = SuperHeroesComics(347),
    series = SuperHeroesSeries(157),
    events = SuperHeroesEvents(445),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg"
)

var marvelSuperHeroesMock = listOf(
    marvelSuperHeroMock1,
    marvelSuperHeroMock2,
    marvelSuperHeroMock3,
)