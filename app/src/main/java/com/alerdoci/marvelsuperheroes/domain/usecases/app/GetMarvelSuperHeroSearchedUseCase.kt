package com.alerdoci.marvelsuperheroes.domain.usecases.app

import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarvelSuperHeroSearchedUseCase @Inject constructor(
    private val repository: MarvelRepository
) {

    suspend operator fun invoke(nameSearched: String?): Flow<List<ModelResult>> =
        repository.getMarvelSuperHeroSearched(nameSearched)
}
