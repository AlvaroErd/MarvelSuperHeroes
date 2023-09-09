package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.impl

import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.datasource.cache.database.SuperHeroesDatabase
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.mappers.toDomain
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.mappers.toDomain
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


open class SuperHeroesCacheDataStoreImpl @Inject constructor(
    private val superHeroesDatabase: SuperHeroesDatabase,
) : SuperheroesDataStore {

    override suspend fun getMarvelSuperHeroesPaging(
        limit: Int,
        offset: Int
    ): Flow<List<ModelResult>> =
        superHeroesDatabase.superHeroesDao().getAllSuperHeroes(limit = limit, offset = offset)
            .map { cacheSuperHeroes ->
                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
            }
//            .onErrorResumeNext { throwable ->
//                // If remote request fails, use remote exception mapper to transform Retrofit exception to an app exception
//                Single.error(RemoteExceptionMapper.getException(throwable))
//            }


    override suspend fun insertOrUpdateSuperHeroes(vararg superHeroesList: ModelResult) {
        superHeroesDatabase.superHeroesDao().insertOrUpdateSuperHeroes(
            *superHeroesList.map { domainSuperHeroesList -> domainSuperHeroesList.toDomain() }
                .toTypedArray()
        )
    }

    override suspend fun getMarvelSuperHeroSearched(
        nameSearched: String?,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> {
        TODO("Not yet implemented")
    }
//        superHeroesDatabase.superHeroesDao().getAllSuperHeroes(limit = limit, offset = offset)
//            .map { cacheSuperHeroes ->
//                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
//            }

    override suspend fun getMarvelSuperHero(
        offset: Int,
        limit: Int,
        superHeroId: Int
    ): Flow<List<ModelResult>> =
        superHeroesDatabase.superHeroesDao().getSuperHeroDetail(superheroId = superHeroId)
            .map { cacheSuperHeroes ->
                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
            }


    override suspend fun getMarvelSuperHeroComics(
        superHeroId: Int,
        offset: Int,
        limit: Int
        ): Flow<List<ModelComicsResult>> = flow {
//        superHeroesDatabase.superHeroesDao().getAllComicsCharacter(superheroId = superHeroId)
//            .map { cacheSuperHeroes ->
//                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
//            }

    }


    override suspend fun insertOrUpdateSuperHeroesComic(vararg superHeroesComicList: ModelComicsResult) {
//        superHeroesDatabase.superHeroesDao().insertOrUpdateComicsSuperheroes(
//            *superHeroesComicList.map { domainSuperHeroesList -> domainSuperHeroesList.toDomain() }
//                .toTypedArray()
//        )
    }
}
