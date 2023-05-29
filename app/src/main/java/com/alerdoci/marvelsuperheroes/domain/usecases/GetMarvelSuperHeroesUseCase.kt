package com.alerdoci.marvelsuperheroes.domain.usecases

import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarvelSuperHeroesUseCase @Inject constructor(
    private val repository: MarvelRepository
) {

    suspend operator fun invoke(): Flow<List<ModelResult>> {
        return repository.getMarvelSuperHeroes()
    }
}