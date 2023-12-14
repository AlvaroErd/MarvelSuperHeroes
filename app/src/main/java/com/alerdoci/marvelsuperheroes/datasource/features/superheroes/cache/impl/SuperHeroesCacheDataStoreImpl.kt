package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.impl

import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.datasource.cache.database.SuperHeroesDatabase
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.mappers.toDomain
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.mappers.toCache
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.mappers.toDomain
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class SuperHeroesCacheDataStoreImpl @Inject constructor(
    private val superHeroesDatabase: SuperHeroesDatabase,
) : SuperheroesDataStore {

    override fun getMarvelSuperHeroesPaging(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<List<ModelResult>> =
        superHeroesDatabase.superHeroesDao().getAllSuperHeroes(limit = limit, offset = offset)
            .map { cacheSuperHeroes ->
                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
            }

    override fun getMarvelSuperHeroesByName(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<List<ModelResult>> =
        superHeroesDatabase.superHeroesDao()
            .getSuperHeroesByName(limit = limit, offset = offset, name = name)
            .map { cacheSuperHeroes ->
                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
            }

    override suspend fun insertOrUpdateSuperHeroes(superHeroesList: List<ModelResult>) {
        try {
            superHeroesDatabase.superHeroesDao().insertOrUpdateSuperHeroes(
                superHeroesList.map { domainSuperHeroesList -> domainSuperHeroesList.toCache() }
            )
        } catch (e: Exception) {
            throw IllegalStateException("${this.javaClass.simpleName} has an error, check here: ${e.message}")
        }
    }

    override fun getMarvelSuperHero(
        superHeroId: Int,
        offset: Int,
        limit: Int
    ): Flow<List<ModelResult>> =
        superHeroesDatabase.superHeroesDao().getSuperHeroDetail(superheroId = superHeroId)
            .map { cacheSuperHeroes ->
                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
            }

    override fun getMarvelSuperHeroComics(
        offset: Int,
        limit: Int,
        superHeroId: Int,
    ): Flow<List<ModelComicsResult>> =
        superHeroesDatabase.superHeroesDao()
            .getAllComicsCharacter(limit = limit, offset = offset, superheroId = superHeroId)
            .map { cacheSuperHeroes ->
                cacheSuperHeroes.map { cacheSuperHero -> cacheSuperHero.toDomain() }
            }

    override suspend fun insertOrUpdateSuperHeroesComic(superHeroesComicList: List<ModelComicsResult>) {
        try {
            superHeroesDatabase.superHeroesDao().insertOrUpdateComicsSuperheroes(
                superHeroesComicList.map { domainSuperHeroesComicList -> domainSuperHeroesComicList.toDomain() }
            )
        } catch (e: Exception) {
            throw IllegalStateException("${this.javaClass.simpleName} has an error, check here: ${e.message}")
        }
    }
}
