package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import android.util.Log
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
import com.alerdoci.marvelsuperheroes.datasource.features.onboarding.cache.settings.DataStoreRepository
import com.alerdoci.marvelsuperheroes.domain.usecases.app.GetMarvelSuperHeroSearchedUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.app.GetMarvelSuperHeroesPagingUseCase
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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

    val searchQuery = MutableStateFlow("")

    val allCharacters: StateFlow<PagingData<ModelResult>> =
        searchQuery.flatMapLatest { query ->
            getMarvelSuperHeroesPagingUseCase().cachedIn(viewModelScope)
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun searchCharacters(query: String) {
        Log.d("Search", "Query: $query")
        if (searchQuery.value != query) {
            searchQuery.value = query
        }
    }

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
