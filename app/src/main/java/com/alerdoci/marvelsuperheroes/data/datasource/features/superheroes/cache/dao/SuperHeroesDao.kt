package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants.MarvelConstants
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroes
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperHeroesDao {

    @Transaction
    @Query(MarvelConstants.QUERY_MARVEL + "LIMIT :limit OFFSET :limit * :offset")
    fun getAllSuperHeroes(limit: Int, offset: Int): Flow<List<CacheSuperHeroes>>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateSuperHeroes(vararg cacheSuperHeroes: CacheSuperHeroes)

    @Query(MarvelConstants.DELETE_ALL_MARVEL)
    suspend fun deleteAllSuperHeroes(): Flow<List<CacheSuperHeroes>>
}