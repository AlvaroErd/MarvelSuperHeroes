package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.impl

import com.alerdoci.marvelsuperheroes.BuildConfig
import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.mappers.toDomain
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.mappers.toDomain
import com.alerdoci.marvelsuperheroes.data.datasource.remote.service.MarvelService
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.di.remote.NetworkModule
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroComic
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class SuperHeroesRemoteDataStoreImpl @Inject constructor(
    private val remoteService: MarvelService,
) : SuperheroesDataStore {

    override suspend fun getMarvelSuperHeroesPaging(
        offset: Int, limit: Int,
    ): Flow<List<SuperHeroesResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superheroes = remoteService.getMarvelSuperHeroes(
            offset = offset * limit,
            limit = limit,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
        )
        if (superheroes.isSuccessful) {
            val results = superheroes.body()?.data?.results
            emit(results?.map { superhero -> superhero.toDomain() } ?: emptyList())
        } else {
            //ToDo Show error message
            emit(emptyList())
        }
    }

    override suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<SuperHeroesResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superhero = remoteService.getMarvelSuperHero(
            superHeroId = superHeroId,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
            offset = offset,
            limit = limit
        )
        if (superhero.isSuccessful) {
            val results = superhero.body()?.data?.results
            if (!results.isNullOrEmpty()) {
                emit(results.map { superhero -> superhero.toDomain() })
            } else {
                emit(emptyList())
            }
        } else {
            emit(emptyList())
        }
    }

    override suspend fun getMarvelSuperHeroSearched(
        nameSearched: String?,
        offset: Int,
        limit: Int,
    ): Flow<List<SuperHeroesResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superhero = remoteService.getMarvelSuperHeroesSearched(
            offset = offset,
            limit = limit,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
            nameSearched = nameSearched
        )
        if (superhero.isSuccessful) {
            val results = superhero.body()?.data?.results
            if (!results.isNullOrEmpty()) {
                emit(results.map { superhero -> superhero.toDomain() })
            } else {
                emit(emptyList())
            }
        } else {
            emit(emptyList())
        }
    }

    override suspend fun getMarvelSuperHeroComics(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<SuperHeroComic>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superhero = remoteService.getMarvelSuperHeroComics(
            superHeroId = superHeroId,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
            offset = offset,
            limit = limit
        )
        if (superhero.isSuccessful) {
            superhero.body()?.let { superHeroComic ->
                emit(listOf(superHeroComic.toDomain()))
            }
        } else {
            emit(emptyList())
        }
    }

    override suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: Array<SuperHeroesResult>) {
        TODO("Not yet implemented")
    }
}
