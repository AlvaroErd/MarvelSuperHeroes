package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.utils.ThemeMode
import com.alerdoci.marvelsuperheroes.data.features.onboarding.cache.settings.DataStoreRepository
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelComics
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelEvents
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelSeries
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroSearchedUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroesPagingUseCase
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
    private val datastore: DataStoreRepository
) : ViewModel() {

    private val _superHeroes by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val superHeroes: StateFlow<ResourceState<*>>
        get() = _superHeroes

    private val _superHeroSearched by lazy { MutableStateFlow<ResourceState<*>>(ResourceState.Idle) }
    val superHeroSearched: StateFlow<ResourceState<*>>
        get() = _superHeroSearched

    fun getMarvelSuperHeroesPager(): Flow<PagingData<ModelResult>> =
        getMarvelSuperHeroesPagingUseCase()
            .catch { error ->
                _superHeroes.update { ResourceState.Error(error) }
            }
            .onStart {
                _superHeroes.update { ResourceState.Loading("") }
            }
            .map { pagingData ->
                delay(1000)
                _superHeroes.update { ResourceState.Success("") }
                pagingData
            }
            .cachedIn(viewModelScope)

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

    private val _theme = MutableLiveData(ThemeMode.Auto)
    val theme: LiveData<ThemeMode> = _theme


    fun setTheme(newTheme: ThemeMode) {
        _theme.postValue(newTheme)
        datastore.putInt(DataStoreRepository.APP_THEME_INT, newTheme.ordinal)
    }

    fun getThemeValue() = datastore.getInt(
        DataStoreRepository.APP_THEME_INT, ThemeMode.Auto.ordinal
    )

    @Composable
    fun getCurrentTheme(): ThemeMode {
        return if (theme.value == ThemeMode.Auto) {
            if (isSystemInDarkTheme()) ThemeMode.Dark else ThemeMode.Light
        } else theme.value!!
    }
}
//Mock
val marvelSuperHeroMock1 = ModelResult(
    id = 1,
    name = "3-D Man",
    description = "Fabulas reformidans viverra invidunt errem vis vitae fastidii. Convenire odio ipsum mutat ligula sociosqu scripserit civibus dicit. Expetenda tortor bibendum instructior maiestatis.",
    comics = ModelComics(55),
    series = ModelSeries(42),
    events = ModelEvents(6),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
)

val marvelSuperHeroMock2 = ModelResult(
    id = 2,
    name = "A-Bomb (HAS)",
    description = "Te theophrastus idque pro hinc mediocritatem metus definiebas rutrum. Sed affert sapientem delicata vituperata at quaestio molestiae turpis gubergren. Neglegentur deserunt ferri dicam feugiat nisl expetendis dolore iuvaret.",
    comics = ModelComics(0),
    series = ModelSeries(1),
    events = ModelEvents(8),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg "
)

val marvelSuperHeroMock3 = ModelResult(
    id = 3,
    name = "A.I.M.",
    description = "Ante vocent suscipit disputationi persecuti noluisse. Dolorem congue dolores finibus ipsum option. Iusto aliquip ligula omittantur purus comprehensam platonem rhoncus.",
    comics = ModelComics(347),
    series = ModelSeries(157),
    events = ModelEvents(445),
    imageFinal = "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg"
)

var marvelSuperHeroesMock = listOf(
    marvelSuperHeroMock1,
    marvelSuperHeroMock2,
    marvelSuperHeroMock3,
)