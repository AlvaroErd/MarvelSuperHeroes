package com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes

import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.OFFSET
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsSuperHeroList
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow

interface SuperheroesDataStore {

    suspend fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int,
    ): Flow<List<ModelResult>>

    suspend fun getMarvelSuperHeroSearched(
        nameSearched: String?,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<ModelResult>>

    suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<ModelResult>>

    suspend fun getMarvelSuperHeroComics(
        superHeroId: Int,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<ModelComicsResult>>

    suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: ModelResult)
    suspend fun insertOrUpdateSuperHeroesComic(vararg superHeroesComicList: ModelComicsResult)
}
