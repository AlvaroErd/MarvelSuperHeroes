package com.alerdoci.marvelsuperheroes.domain.usecases

import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsSuperHeroList
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarvelSuperHeroComicsUseCase @Inject constructor(
    private val repository: MarvelRepository
) {

    suspend operator fun invoke(superHeroId: Int): Flow<List<ModelComicsSuperHeroList>> =
        repository.getMarvelSuperHeroComics(superHeroId)
}