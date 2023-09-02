package com.alerdoci.marvelsuperheroes.domain.usecases

import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarvelSuperHeroUseCase @Inject constructor(
    private val repository: MarvelRepository
) {

    suspend operator fun invoke(superHeroId: Int): Flow<List<ModelResult>> {
        return repository.getMarvelSuperHero(superHeroId)
    }
}
