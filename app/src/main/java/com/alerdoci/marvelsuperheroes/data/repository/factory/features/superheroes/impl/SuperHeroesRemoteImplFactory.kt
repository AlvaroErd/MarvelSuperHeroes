package com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.impl

import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.factory.SuperHeroesDataFactory
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

open class SuperHeroesRemoteImplFactory @Inject constructor(
    private val factory: SuperHeroesDataFactory
) : MarvelRepository {
    override suspend fun getMarvelSuperHeroesPaging(
        nameSearched: String?,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> = channelFlow {
        factory.cacheDataStore.getMarvelSuperHeroesPaging(
            offset = offset,
            limit = limit
        ).collectLatest { superheroes ->
            if (superheroes.isEmpty()) {
                try {
                    factory.remoteDataStore.getMarvelSuperHeroesPaging(
                        offset = offset,
                        limit = limit
                    ).collectLatest { remoteSuperHeroesPaging ->
                        factory.cacheDataStore.insertOrUpdateSuperHeroes(*remoteSuperHeroesPaging.toTypedArray())
                        send(remoteSuperHeroesPaging)
                    }

                } catch (exception: Exception) {
                    send(emptyList())
                }
            } else {
                send(superheroes)
            }
        }
    }

    override suspend fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> = channelFlow {
        factory.cacheDataStore.getMarvelSuperHero(
            superHeroId = superHeroId
        )
            .collectLatest { superheroes ->
                if (superheroes.isEmpty()) {
                    try {
                        factory.remoteDataStore.getMarvelSuperHero(
                            superHeroId = superHeroId
                        ).collectLatest { remoteSuperHero ->
                            factory.cacheDataStore.insertOrUpdateSuperHeroes(*remoteSuperHero.toTypedArray())
                            send(remoteSuperHero)
                        }

                    } catch (exception: Exception) {
                        send(emptyList())
                    }
                } else {
                    send(superheroes)
                }
            }
    }

    override suspend fun getMarvelSuperHeroComics(
        offset: Int,
        limit: Int,
        superHeroId: Int
    ): Flow<List<ModelComicsResult>> = channelFlow {
        factory.cacheDataStore.getMarvelSuperHeroComics(
            offset = offset,
            limit = limit,
            superHeroId = superHeroId
        ).collectLatest { superheroComic ->
            if (superheroComic.isEmpty()) {
                try {
                    factory.remoteDataStore.getMarvelSuperHeroComics(
                        offset = offset,
                        limit = limit,
                        superHeroId = superHeroId,
                    ).collectLatest { remoteSuperHeroComics ->
                        factory.cacheDataStore.insertOrUpdateSuperHeroesComic(*remoteSuperHeroComics.toTypedArray())
                        send(remoteSuperHeroComics)
                    }

                } catch (exception: Exception) {
                    send(emptyList())
                }
            } else {
                send(superheroComic)
            }
        }
    }
}
