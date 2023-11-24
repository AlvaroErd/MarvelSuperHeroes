package com.alerdoci.marvelsuperheroes.datasource.cache.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsDateListConverter
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsUrlsListConverter
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.constants.MarvelConstants.DATABASE_NAME
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.dao.SuperHeroesDao
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult
import java.util.concurrent.Executors

@Database(
    entities = [
        CacheSuperHeroesResult::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    CacheComicsDateListConverter::class,
    CacheComicsUrlsListConverter::class,
)
abstract class SuperHeroesDatabase : RoomDatabase() {

    private var INSTANCE: SuperHeroesDatabase? = null
    private val sLock = Any()

    abstract fun superHeroesDao(): SuperHeroesDao

    //K.C.A.T
    fun getInstance(context: Context): SuperHeroesDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SuperHeroesDatabase::class.java, DATABASE_NAME
                    ).enableMultiInstanceInvalidation()
                        .setQueryCallback(object : QueryCallback {
                            override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                                if (com.alerdoci.marvelsuperheroes.BuildConfig.DEBUG)
                                    Log.i(DATABASE_NAME, "$DATABASE_NAME: $sqlQuery, $bindArgs")
                            }
                        }, Executors.newSingleThreadExecutor()).build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

    //region A.F.A for automigrations
    companion object {

        val migrations: List<Migration> = listOf(
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // TODO Create some alter/create table, if needed
                    database.execSQL("")
                }
            },
            // Some Migrations [...]
        )
    }
    //endregion

}
