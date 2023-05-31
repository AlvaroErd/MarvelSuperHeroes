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
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelThumbnail
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
    name = "Baby Groot",
    description = "Fabulas reformidans viverra invidunt errem vis vitae fastidii. Convenire odio ipsum mutat ligula sociosqu scripserit civibus dicit. Expetenda tortor bibendum instructior maiestatis.",
    modified = "",
    thumbnail = ModelThumbnail(
        ".jpg",
        "https://ae01.alicdn.com/kf/S25773c4386c04f95aad6442731a4e601h"
    ),
    resourceURI = "",
    comics = ModelComics(null, null, emptyList(), null),
    series = ModelSeries(null, null, emptyList(), null),
    stories = ModelStories(null, null, emptyList(), null),
    events = ModelEvents(null, null, emptyList(), null),
    urls = listOf(
        ModelUrl("tipo1", "https://ejemplo1.com"),
        ModelUrl("tipo2", "https://ejemplo2.com"),
        ModelUrl("tipo3", "https://ejemplo3.com"),
    )
)

val marvelSuperHeroMock2 = ModelResult(
    id = 1,
    name = "Baby Rocket",
    description = "Te theophrastus idque pro hinc mediocritatem metus definiebas rutrum. Sed affert sapientem delicata vituperata at quaestio molestiae turpis gubergren. Neglegentur deserunt ferri dicam feugiat nisl expetendis dolore iuvaret.",
    modified = "",
    thumbnail = ModelThumbnail(
        ".jpg",
        "https://cdn.vox-cdn.com/thumbor/3OJIOHLH8C5FScmhPCmuSnk2heE=/0x0:4096x1716/1200x800/filters:focal(1721x531:2375x1185)/cdn.vox-cdn.com/uploads/chorus_image/image/72270263/FBK0100_TRL_comp_FRA_v0213.1079_R.0"
    ),
    resourceURI = "",
    comics = ModelComics(null, null, emptyList(), null),
    series = ModelSeries(null, null, emptyList(), null),
    stories = ModelStories(null, null, emptyList(), null),
    events = ModelEvents(null, null, emptyList(), null),
    urls = listOf(
        ModelUrl("tipo1", "https://ejemplo1.com"),
        ModelUrl("tipo2", "https://ejemplo2.com"),
        ModelUrl("tipo3", "https://ejemplo3.com"),
    )
)

val marvelSuperHeroMock3 = ModelResult(
    id = 1,
    name = "Baby Yoda",
    description = "Ante vocent suscipit disputationi persecuti noluisse. Dolorem congue dolores finibus ipsum option. Iusto aliquip ligula omittantur purus comprehensam platonem rhoncus.",
    modified = "",
    thumbnail = ModelThumbnail(
        ".jpg",
        "https://i0.wp.com/imgs.hipertextual.com/wp-content/uploads/2020/01/hipertextual-es-figura-baby-yoda-mas-real-que-podras-comprar-2020062519.jpeg?fit=1920%2C1080&quality=50&strip=all&ssl=1"
    ),
    resourceURI = "",
    comics = ModelComics(null, null, emptyList(), null),
    series = ModelSeries(null, null, emptyList(), null),
    stories = ModelStories(null, null, emptyList(), null),
    events = ModelEvents(null, null, emptyList(), null),
    urls = listOf(
        ModelUrl("tipo1", "https://ejemplo1.com"),
        ModelUrl("tipo2", "https://ejemplo2.com"),
        ModelUrl("tipo3", "https://ejemplo3.com"),
    )
)

var marvelSuperHeroesMock = listOf(
    marvelSuperHeroMock1,
    marvelSuperHeroMock2,
    marvelSuperHeroMock3,
)
