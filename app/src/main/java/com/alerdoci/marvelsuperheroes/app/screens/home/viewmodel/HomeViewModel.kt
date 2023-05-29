package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroesPagingUseCase
import com.alerdoci.marvelsuperheroes.domain.usecases.GetMarvelSuperHeroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMarvelSuperHeroesUseCase: GetMarvelSuperHeroesUseCase,
    private val getMarvelSuperHeroesPagingUseCase: GetMarvelSuperHeroesPagingUseCase
) : ViewModel() {

}