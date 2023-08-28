package com.alerdoci.marvelsuperheroes.data.datasource.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants.MarvelConstants.TABLE_NAME
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.dao.SuperHeroesDao
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroes

/* Needed by room migrations
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
*/

@Database(
    entities = [
        CacheSuperHeroes::class,
//    SuperHeroEntity::class
//    SuperHeroComicEntity::class
    ],
    version = 1, exportSchema = true
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
                        SuperHeroesDatabase::class.java, TABLE_NAME
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

    //region A.F.A for automigrations
    companion object {

        val migrations: List<Migration> = listOf(
            /*
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // TODO Create some alter/create table, if needed
                    database.execSQL("")
                }
            },
            // Some Migrations [...]
             */
        )
    }
    //endregion

}
