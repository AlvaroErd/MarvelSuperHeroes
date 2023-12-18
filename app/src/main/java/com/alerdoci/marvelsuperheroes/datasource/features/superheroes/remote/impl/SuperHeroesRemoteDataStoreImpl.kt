package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.impl

import com.alerdoci.marvelsuperheroes.BuildConfig
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.datasource.di.remote.NetworkModule
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.mappers.toDomain
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.mappers.toDomain
import com.alerdoci.marvelsuperheroes.datasource.remote.service.MarvelService
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.OFFSET
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class SuperHeroesRemoteDataStoreImpl @Inject constructor(
    private val remoteService: MarvelService,
) : SuperheroesDataStore {

    override fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<List<ModelResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superheroes = remoteService.getMarvelSuperHeroes(
            offset = offset * limit,
            limit = limit,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
            name = name
        )
        if (superheroes.isSuccessful) {
            val results = superheroes.body()?.data?.results
            emit(results?.map { superhero -> superhero.toDomain() } ?: emptyList())
        } else {
            emit(emptyList())
            throw throw IllegalStateException(
                "ErrorCode: ${superheroes.code()}, Message:${
                    superheroes.errorBody()?.string()
                }"
            )
        }
    }
    override fun getMarvelSuperHeroesByName(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<List<ModelResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superheroes = remoteService.getMarvelSuperHeroes(
            offset = offset * limit,
            limit = limit,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            hash = NetworkModule.getHash(timestamp),
            name = name
        )
        if (superheroes.isSuccessful) {
            val results = superheroes.body()?.data?.results
            emit(results?.map { superhero -> superhero.toDomain() } ?: emptyList())
        } else {
            emit(emptyList())
            throw throw IllegalStateException(
                "ErrorCode: ${superheroes.code()}, Message:${
                    superheroes.errorBody()?.string()
                }"
            )
        }
    }

    override fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superhero = remoteService.getMarvelSuperHero(
            superHeroId = superHeroId,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
            offset = OFFSET,
            limit = PAGE_SIZE
        )
        if (superhero.isSuccessful) {
            val results = superhero.body()?.data?.results
            if (!results.isNullOrEmpty()) {
                emit(results.map { superheroRemote -> superheroRemote.toDomain() })
            } else {
                emit(emptyList())
            }
        } else {
            emit(emptyList())
            throw throw IllegalStateException(
                "ErrorCode: ${superhero.code()}, Message:${
                    superhero.errorBody()?.string()
                }"
            )
        }
    }

    override fun getMarvelSuperHeroComics(
        offset: Int,
        limit: Int,
        superHeroId: Int,
    ): Flow<List<ModelComicsResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superhero = remoteService.getMarvelSuperHeroComics(
            superHeroId = superHeroId,
            offset = offset * limit,
            limit = limit,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
        )
        if (superhero.isSuccessful) {
            val results = superhero.body()?.data?.results
            emit(results?.map { superheroComic -> superheroComic.toDomain(superHeroId = superHeroId) } ?: emptyList())
        } else {
            emit(emptyList())
            throw throw IllegalStateException(
                "ErrorCode: ${superhero.code()}, Message:${
                    superhero.errorBody()?.string()
                }"
            )
        }
    }

}
