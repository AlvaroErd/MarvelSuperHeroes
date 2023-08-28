package com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes

import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.OFFSET
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroComic
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import kotlinx.coroutines.flow.Flow

interface SuperheroesDataStore {

    suspend fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int,
    ): Flow<List<SuperHeroesResult>>

    suspend fun getMarvelSuperHeroSearched(
        nameSearched: String?,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<SuperHeroesResult>>

    suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<SuperHeroesResult>>

    suspend fun getMarvelSuperHeroComics(
        superHeroId: Int,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<SuperHeroComic>>

    suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: Array<SuperHeroesResult>)
}
