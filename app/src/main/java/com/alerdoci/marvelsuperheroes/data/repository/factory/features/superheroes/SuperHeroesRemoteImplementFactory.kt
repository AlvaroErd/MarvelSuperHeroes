package com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes

import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.factory.SuperHeroesDataFactory
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroComic
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

open class SuperHeroesRemoteImplementFactory @Inject constructor(
    private val factory: SuperHeroesDataFactory
) : MarvelRepository {

    override suspend fun getMarvelSuperHeroesPaging(offset: Int, limit: Int): Flow<List<SuperHeroesResult>> = channelFlow {
        factory.cacheDataStore.getMarvelSuperHeroesPaging(offset = offset, limit = limit).collectLatest { superheroes ->
            try {
                if (superheroes.isEmpty()) {
                    factory.remoteDataStore.getMarvelSuperHeroesPaging(offset = offset, limit = limit)
                        .collectLatest { remoteSuperHeroesPaging ->
                            factory.cacheDataStore.insertOrUpdateSuperHeroes(*arrayOf(remoteSuperHeroesPaging.toTypedArray()))
                            send(remoteSuperHeroesPaging)
                        }
                } else {
                    send(superheroes)
                }
            } catch (_: Exception) {
                send(emptyList())
            }
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
}
