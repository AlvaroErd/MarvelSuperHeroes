package com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.impl

import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.factory.SuperHeroesDataFactory
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

open class SuperHeroesRemoteImplFactory @Inject constructor(
    private val factory: SuperHeroesDataFactory
) : MarvelRepository {

    override fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int,
        name: String?,
    ): Flow<List<ModelResult>> = channelFlow {
        try {
            val localData = if (!name.isNullOrEmpty()) {
                factory.cacheDataStore.getMarvelSuperHeroesByName(offset, limit, name)
            } else {
                factory.cacheDataStore.getMarvelSuperHeroesPaging(offset, limit, null)
            }

            localData.collect { superheroes ->
                if (superheroes.isEmpty()) {
                    if (!name.isNullOrEmpty()) {
                        getSuperHeroesByName(this, offset, limit, name)
                    } else {
                        getSuperHeroesPaging(this, offset, limit)
                    }
                } else {
                    send(superheroes)
                }
            }
        } catch (exception: Exception) {
            send(emptyList())
        }
    }

    private suspend fun getSuperHeroesByName(
        channel: SendChannel<List<ModelResult>>,
        offset: Int,
        limit: Int,
        name: String?
    ) {
        try {
            val remoteResultSearch = factory.remoteDataStore.getMarvelSuperHeroesByName(
                offset = offset,
                limit = limit,
                name = name
            )
            remoteResultSearch.collectLatest { remoteSuperHeroSearched ->
                factory.cacheDataStore.insertOrUpdateSuperHeroes(remoteSuperHeroSearched)
                channel.send(remoteSuperHeroSearched)
            }
        } catch (exception: Exception) {
            channel.send(emptyList())
        }
    }

    private suspend fun getSuperHeroesPaging(
        channel: SendChannel<List<ModelResult>>,
        offset: Int,
        limit: Int
    ) {
        try {
            val remoteResult = factory.remoteDataStore.getMarvelSuperHeroesPaging(
                offset = offset,
                limit = limit,
                name = null
            )
            remoteResult.collectLatest { remoteSuperHeroesPaging ->
                factory.cacheDataStore.insertOrUpdateSuperHeroes(remoteSuperHeroesPaging)
                channel.send(remoteSuperHeroesPaging)
            }
        } catch (exception: Exception) {
            channel.send(emptyList())
        }
    }

    override fun getMarvelSuperHero(
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
                            factory.cacheDataStore.insertOrUpdateSuperHeroes(remoteSuperHero)
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

    override fun getMarvelSuperHeroComics(
        offset: Int,
        limit: Int,
        superHeroId: Int
    ): Flow<List<ModelComicsResult>> = channelFlow {
        try {
            val localData = factory.cacheDataStore.getMarvelSuperHeroComics(
                offset = offset,
                limit = limit,
                superHeroId = superHeroId
            )

            localData.collect { superheroComic ->
                if (superheroComic.isEmpty()) {
                    try {
                        val remoteResult = factory.remoteDataStore.getMarvelSuperHeroComics(
                            offset = offset,
                            limit = limit,
                            superHeroId = superHeroId,
                        )
                        remoteResult.collectLatest { remoteSuperHeroComics ->
                            factory.cacheDataStore.insertOrUpdateSuperHeroesComic(remoteSuperHeroComics)
                            send(remoteSuperHeroComics)
                        }
                    } catch (exception: Exception) {
                        send(emptyList())
                    }
                } else {
                    send(superheroComic)
                }
            }
        } catch (exception: Exception) {
            send(emptyList())
        }
    }

}
