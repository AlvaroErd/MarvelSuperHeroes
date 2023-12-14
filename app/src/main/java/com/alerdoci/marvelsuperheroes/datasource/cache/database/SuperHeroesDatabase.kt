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

    /*    //K.C.A.T
        private var INSTANCE: SuperHeroesDatabase? = null
        private val sLock = Any()

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
        }*/

    //region A.F.A for automigrations
    /*    companion object {

            val migrations: List<Migration> = listOf(
                object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("")
                    }
                },
                // Some Migrations [...]
            )
        }*/
    //endregion

}
