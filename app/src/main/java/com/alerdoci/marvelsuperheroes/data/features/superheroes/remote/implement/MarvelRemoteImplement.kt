package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.implement

import com.alerdoci.marvelsuperheroes.BuildConfig
import com.alerdoci.marvelsuperheroes.data.di.NetworkModule
import com.alerdoci.marvelsuperheroes.data.features.superheroes.mappers.toDomain
import com.alerdoci.marvelsuperheroes.data.service.MarvelService
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

    override suspend fun getMarvelSuperHeroes(
        offset: Int, limit: Int
    ): Flow<List<ModelResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        try {
            val superheroes = remoteService.getMarvelSuperHeroes(
                BuildConfig.API_KEY_PUBLIC,
                timestamp,
                NetworkModule.getHash(timestamp),
                offset = offset,
                limit = limit
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
        } catch (e: Exception) {
            emit(
                emptyList(),
            )
        }
    }

    override suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        try {
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
        } catch (e: Exception) {
            emit(
                emptyList(),
            )
        }
    }
}
