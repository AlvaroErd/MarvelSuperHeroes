package com.alerdoci.marvelsuperheroes.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.dao.SuperHeroesDao
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult

@Database(
    entities = [
        CacheSuperHeroesResult::class,
        CacheComicsResult::class,
    ],
    version = 1,
    exportSchema = true,
//    autoMigrations = [ AutoMigration(from = 1, to = 2)]
)

abstract class SuperHeroesDatabase : RoomDatabase() {

    abstract fun superHeroesDao(): SuperHeroesDao

}
