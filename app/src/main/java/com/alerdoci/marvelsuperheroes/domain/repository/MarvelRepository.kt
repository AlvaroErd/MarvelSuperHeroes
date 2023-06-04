package com.alerdoci.marvelsuperheroes.domain.repository

import com.alerdoci.marvelsuperheroes.data.constants.Constants.Companion.OFFSET
import com.alerdoci.marvelsuperheroes.data.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    suspend fun getMarvelSuperHeroes(
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<ModelResult>>

    suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<ModelResult>>
}