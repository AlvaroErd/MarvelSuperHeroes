package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.constants.MarvelConstants.QUERY_SUPERHEROES
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.constants.MarvelConstants.QUERY_SUPERHEROES_COMICS
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperHeroesDao {

    // region SuperHeroes and SuperHero Detail
    @Transaction
    @Query("$QUERY_SUPERHEROES LIMIT :limit OFFSET :limit * :offset")
    fun getAllSuperHeroes(limit: Int, offset: Int): Flow<List<CacheSuperHeroesResult>>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateSuperHeroes(vararg cacheSuperHeroes: CacheSuperHeroesResult)

    @Delete
    suspend fun deleteAllSuperHeroes(vararg cacheSuperHeroes: CacheSuperHeroesResult)

    @Transaction
    @Query("$QUERY_SUPERHEROES WHERE id = :superheroId")
    fun getSuperHeroDetail(superheroId: Int): Flow<List<CacheSuperHeroesResult>>

    //endregion

/*    // region SuperHeroesComics
    @Transaction
    @Query("$QUERY_SUPERHEROES_COMICS WHERE  id = :superheroId")
    fun getAllComicsCharacter(superheroId: Int): Flow<List<CacheComicsResult>>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateComicsSuperheroes(vararg cacheComicsResult: CacheComicsResult)

    @Delete
    suspend fun deleteAllSuperHeroesComics(vararg cacheComicsResult: CacheComicsResult)

    //endregion*/

}
