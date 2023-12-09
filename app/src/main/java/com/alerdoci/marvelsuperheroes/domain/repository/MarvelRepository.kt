package com.alerdoci.marvelsuperheroes.domain.repository

import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.OFFSET
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    suspend fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int,
        name: String?,
    ): Flow<List<ModelResult>>

    suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<ModelResult>>

    suspend fun getMarvelSuperHeroComics(
        offset: Int,
        limit: Int,
        superHeroId: Int,
    ): Flow<List<ModelComicsResult>>
}
