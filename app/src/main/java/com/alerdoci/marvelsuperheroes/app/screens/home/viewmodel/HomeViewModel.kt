package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.states.error.AppAction
import com.alerdoci.marvelsuperheroes.app.common.states.error.AppError
import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorBundle
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelComics
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelEvents
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelSeries
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelStories
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelUrl
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroSearchedUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMarvelSuperHeroesPagingUseCase: GetMarvelSuperHeroesPagingUseCase,
    private val getMarvelSuperHeroSearchedUseCase: GetMarvelSuperHeroSearchedUseCase,
) : ViewModel() {

    private val _superHeroes by lazy {
        MutableStateFlow<ResourceState<PagingData<ModelResult>>>(
            ResourceState.Idle()
        )
    }
    val superHeroes: StateFlow<ResourceState<PagingData<ModelResult>>>
        get() = _superHeroes

    private val _superHeroSearched by lazy {
        MutableStateFlow<ResourceState<List<ModelResult>>>(
            ResourceState.Idle()
        )
    }
    val superHeroSearched: StateFlow<ResourceState<List<ModelResult>>>
        get() = _superHeroSearched

    fun getMarvelSuperHeroesPager(): Flow<PagingData<ModelResult>> =
        getMarvelSuperHeroesPagingUseCase()
            .catch { error ->
                _superHeroes.value = ResourceState.Error(errorBundleFromThrowable(error))
            }
            .onStart {
                _superHeroes.value = ResourceState.Loading()
            }
            .map { pagingData ->
                delay(1000)
                _superHeroes.value = ResourceState.Success(pagingData)
                pagingData
            }

//    fun getSuperHeroSearched(nameSearched: String?) {
//        _superHeroSearched.value = ResourceState.Loading()
//
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val superHero = getMarvelSuperHeroSearchedUseCase(nameSearched = nameSearched)
//                _superHeroSearched.value = if (superHero.isNotEmpty())
//                    ResourceState.Success(superHero)
//                else
//                    ResourceState.Error(InvalidObjectException("SuperHero not found :("))
//            } catch (error: Throwable) {
//                _superHeroSearched.value = ResourceState.Error(errorBundleFromThrowable(error))
//            }
//        }
//    }

    private fun errorBundleFromThrowable(throwable: Throwable): ErrorBundle {
        // Implement this function to create an ErrorBundle from the Throwable
        // You can customize the ErrorBundle fields as needed
        return ErrorBundle(
            throwable = throwable,
            stringId = R.string.error, // Provide a default error message resource ID
            debugMessage = "Error: ${throwable.message}",
            appAction = AppAction.UNKNOWN,
            appError = AppError.UNKNOWN
        )
    }
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
