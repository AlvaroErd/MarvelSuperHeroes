package com.alerdoci.marvelsuperheroes.domain.usecases

import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarvelSuperHeroSearchedUseCase @Inject constructor(
    private val repository: MarvelRepository
) {

    suspend operator fun invoke(nameSearched: String?): Flow<List<SuperHeroesResult>> =
        repository.getMarvelSuperHeroSearched(nameSearched)
}