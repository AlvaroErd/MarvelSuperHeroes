package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelComics
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelEvents
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelSeries
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelStories
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelUrl
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

//Mock
val marvelSuperHeroMock1 = ModelResult(
    id = 1,
    name = "3-D Man",
    description = "Fabulas reformidans viverra invidunt errem vis vitae fastidii. Convenire odio ipsum mutat ligula sociosqu scripserit civibus dicit. Expetenda tortor bibendum instructior maiestatis.",
    modified = "",
    resourceURI = "",
    comics = ModelComics(null, null, emptyList(), null),
    series = ModelSeries(null, null, emptyList(), null),
    stories = ModelStories(null, null, emptyList(), null),
    events = ModelEvents(null, null, emptyList(), null),
    urls = listOf(
        ModelUrl("", ""),
        ModelUrl("", "")
    ),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
)

val marvelSuperHeroMock2 = ModelResult(
    id = 2,
    name = "A-Bomb (HAS)",
    description = "Te theophrastus idque pro hinc mediocritatem metus definiebas rutrum. Sed affert sapientem delicata vituperata at quaestio molestiae turpis gubergren. Neglegentur deserunt ferri dicam feugiat nisl expetendis dolore iuvaret.",
    modified = "",
    resourceURI = "",
    comics = ModelComics(null, null, emptyList(), null),
    series = ModelSeries(null, null, emptyList(), null),
    stories = ModelStories(null, null, emptyList(), null),
    events = ModelEvents(null, null, emptyList(), null),
    urls = listOf(
        ModelUrl("", ""),
        ModelUrl("", "")
    ),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg "
)

val marvelSuperHeroMock3 = ModelResult(
    id = 3,
    name = "A.I.M.",
    description = "Ante vocent suscipit disputationi persecuti noluisse. Dolorem congue dolores finibus ipsum option. Iusto aliquip ligula omittantur purus comprehensam platonem rhoncus.",
    modified = "",
    resourceURI = "",
    comics = ModelComics(null, null, emptyList(), null),
    series = ModelSeries(null, null, emptyList(), null),
    stories = ModelStories(null, null, emptyList(), null),
    events = ModelEvents(null, null, emptyList(), null),
    urls = listOf(
        ModelUrl("", ""),
        ModelUrl("", "")
    ),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg"
)

var marvelSuperHeroesMock = listOf(
    marvelSuperHeroMock1,
    marvelSuperHeroMock2,
    marvelSuperHeroMock3,
)
