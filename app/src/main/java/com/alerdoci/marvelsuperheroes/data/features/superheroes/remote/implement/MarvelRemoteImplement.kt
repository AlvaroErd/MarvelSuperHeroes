package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.implement

import com.alerdoci.marvelsuperheroes.BuildConfig
import com.alerdoci.marvelsuperheroes.data.di.NetworkModule
import com.alerdoci.marvelsuperheroes.data.features.superherocomics.mappers.toDomain
import com.alerdoci.marvelsuperheroes.data.features.superheroes.mappers.toDomain
import com.alerdoci.marvelsuperheroes.data.service.MarvelService
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsSuperHeroList
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MarvelRepositoryImplement @Inject constructor(
    private val remoteService: MarvelService,
) : MarvelRepository {

    override suspend fun getMarvelSuperHeroesPaging(
        offset: Int, limit: Int,
    ): Flow<List<ModelResult>> = flow {
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
            if (!results.isNullOrEmpty()) {
                emit(results.map { superhero -> superhero.toDomain() })
            } else {
                emit(emptyList())
            }
        } else {
            emit(emptyList())
        }
    }

    override suspend fun getMarvelSuperHero(
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
    ): Flow<List<ModelResult>> = flow {
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
    ): Flow<List<ModelComicsSuperHeroList>> = flow {
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
}
