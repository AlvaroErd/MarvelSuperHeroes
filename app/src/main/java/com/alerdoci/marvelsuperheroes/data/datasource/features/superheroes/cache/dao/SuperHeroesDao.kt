package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants.MarvelConstants.TABLE_NAME
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperHeroesDao {

    @Transaction
    @Query("SELECT * FROM $TABLE_NAME LIMIT :limit OFFSET :limit * :offset")
    fun getAllSuperHeroes(limit: Int, offset: Int): Flow<List<CacheSuperHeroesResult>>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateSuperHeroes(vararg cacheSuperHeroes: CacheSuperHeroesResult)

    @Delete
    suspend fun deleteAllSuperHeroes(vararg cacheSuperHeroes: CacheSuperHeroesResult)
}
