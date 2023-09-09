package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.impl

import com.alerdoci.marvelsuperheroes.BuildConfig
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.datasource.di.remote.NetworkModule
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.mappers.toDomain
import com.alerdoci.marvelsuperheroes.datasource.remote.errorhandling.RemoteExceptionMapper
import com.alerdoci.marvelsuperheroes.datasource.remote.service.MarvelService
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.OFFSET
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class SuperHeroesRemoteDataStoreImpl @Inject constructor(
    private val remoteService: MarvelService,
) : SuperheroesDataStore {

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
            emit(results?.map { superhero -> superhero.toDomain() } ?: emptyList())
        } else {
            //ToDo Show error message
            emit(emptyList())
//            Completable.error(RemoteExceptionMapper.getException(superheroes.message))
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
            offset = OFFSET,
            limit = PAGE_SIZE
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
            offset = OFFSET,
            limit = PAGE_SIZE,
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
    ): Flow<List<ModelComicsResult>> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val superhero = remoteService.getMarvelSuperHeroComics(
            superHeroId = superHeroId,
            BuildConfig.API_KEY_PUBLIC,
            timestamp,
            NetworkModule.getHash(timestamp),
            offset = OFFSET,
            limit = PAGE_SIZE
        )
        if (superhero.isSuccessful) {
            superhero.body()?.let { superHeroComic ->
//                emit(listOf(superHeroComic.toDomain()))
            }
        } else {
            emit(emptyList())
        }
    }

    override suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: ModelResult) {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrUpdateSuperHeroesComic(vararg superHeroesComicList: ModelComicsResult) {
        TODO("Not yet implemented")
    }
}
