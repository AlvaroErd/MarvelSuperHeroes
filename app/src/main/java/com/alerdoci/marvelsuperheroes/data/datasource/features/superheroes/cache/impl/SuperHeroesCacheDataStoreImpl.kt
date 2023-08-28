package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.impl

import com.alerdoci.marvelsuperheroes.data.datasource.cache.database.SuperHeroesDatabase
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.mappers.toCache
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.mappers.toDomain
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroComic
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class SuperHeroesCacheDataStoreImpl @Inject constructor(
    private val superHeroesDatabase: SuperHeroesDatabase,
) : SuperheroesDataStore {

    override suspend fun getMarvelSuperHeroesPaging(limit: Int, offset: Int): Flow<List<SuperHeroesResult>> = flow {
        superHeroesDatabase.superHeroesDao().getAllSuperHeroes(limit = limit, offset = offset).map { cacheSuperHeroes ->
            cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
        }
    }

    override suspend fun getMarvelSuperHeroSearched(nameSearched: String?, offset: Int, limit: Int): Flow<List<SuperHeroesResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarvelSuperHero(superHeroId: Int, offset: Int, limit: Int): Flow<List<SuperHeroesResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarvelSuperHeroComics(superHeroId: Int, offset: Int, limit: Int): Flow<List<SuperHeroComic>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: Array<SuperHeroesResult>) {
        superHeroesDatabase.superHeroesDao().insertOrUpdateSuperHeroes(
            *superHeroesList.map { domainSuperHeroesList -> domainSuperHeroesList.toCache() }.toTypedArray()
        )
    }
}
