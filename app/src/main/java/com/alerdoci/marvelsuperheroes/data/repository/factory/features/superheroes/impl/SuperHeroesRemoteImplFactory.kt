package com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.impl

import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.factory.SuperHeroesDataFactory
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsSuperHeroList
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

open class SuperHeroesRemoteImplFactory @Inject constructor(
    private val factory: SuperHeroesDataFactory
) : MarvelRepository {
    override suspend fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> = channelFlow {
        factory.cacheDataStore.getMarvelSuperHeroesPaging(offset = offset, limit = limit)
            .collectLatest { superheroes ->
                if (superheroes.isEmpty()) {
                    try {
                        factory.remoteDataStore.getMarvelSuperHeroesPaging(
                            offset = offset,
                            limit = limit
                        ).collectLatest { remoteSuperHeroesPaging ->
                            factory.cacheDataStore.insertOrUpdateSuperHeroes(*remoteSuperHeroesPaging.toTypedArray())
                            send(remoteSuperHeroesPaging)
                        }

                    } catch (_: Exception) {
                        send(emptyList())
                    }
                } else {
                    send(superheroes)
                }
            }
    }

    override suspend fun getMarvelSuperHeroSearched(
        nameSearched: String?,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarvelSuperHeroComics(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<ModelComicsSuperHeroList>> {
        TODO("Not yet implemented")
    }
}
