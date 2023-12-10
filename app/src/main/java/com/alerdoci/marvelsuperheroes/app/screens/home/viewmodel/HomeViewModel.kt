package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alerdoci.marvelsuperheroes.domain.usecases.app.GetMarvelSuperHeroesPagingUseCase
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMarvelSuperHeroesPagingUseCase: GetMarvelSuperHeroesPagingUseCase,
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val allCharacters: StateFlow<PagingData<ModelResult>> =
        searchQuery.flatMapLatest { query ->
            getMarvelSuperHeroesPagingUseCase(query).cachedIn(viewModelScope)
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun searchCharacters(query: String) {
        Log.d("Search", "Query: $query")
        if (searchQuery.value != query) {
            searchQuery.value = query
        }
    }

}
