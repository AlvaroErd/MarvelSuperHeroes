package com.alerdoci.marvelsuperheroes.domain.usecases.app

import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsSuperHeroList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarvelSuperHeroComicsUseCase @Inject constructor(
    private val repository: MarvelRepository
) {

    suspend operator fun invoke(superHeroId: Int): Flow<List<ModelComicsSuperHeroList>> =
        repository.getMarvelSuperHeroComics(superHeroId)
}
