package com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes

import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.OFFSET
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow

interface SuperheroesDataStore {

    fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<List<ModelResult>>

    fun getMarvelSuperHeroesByName(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<List<ModelResult>>

    fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int = OFFSET,
        limit: Int = PAGE_SIZE
    ): Flow<List<ModelResult>>

    fun getMarvelSuperHeroComics(
        offset: Int,
        limit: Int,
        superHeroId: Int,
    ): Flow<List<ModelComicsResult>>

    suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: ModelResult)
    suspend fun insertOrUpdateSuperHeroesComic(vararg superHeroesComicList: ModelComicsResult)
}
