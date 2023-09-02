package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.impl

import com.alerdoci.marvelsuperheroes.data.datasource.cache.database.SuperHeroesDatabase
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.mappers.toDomain
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.mappers.toYes
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


open class SuperHeroesCacheDataStoreImpl @Inject constructor(
    private val superHeroesDatabase: SuperHeroesDatabase,
) : SuperheroesDataStore {

    override suspend fun getMarvelSuperHeroesPaging(limit: Int, offset: Int): Flow<List<ModelResult>> = superHeroesDatabase.superHeroesDao().getAllSuperHeroes(limit = limit, offset = offset).map { cacheSuperHeroes ->
        cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
    }

    override suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: ModelResult) {
        superHeroesDatabase.superHeroesDao().insertOrUpdateSuperHeroes(
            *superHeroesList.map { domainSuperHeroesList -> domainSuperHeroesList.toYes() }.toTypedArray()
        )
    }

    override suspend fun getMarvelSuperHeroSearched(nameSearched: String?, offset: Int, limit: Int): Flow<List<ModelResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarvelSuperHero(superHeroId: Int, offset: Int, limit: Int): Flow<List<ModelResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarvelSuperHeroComics(superHeroId: Int, offset: Int, limit: Int): Flow<List<ModelComicsResult>> {
        TODO("Not yet implemented")
    }


}
