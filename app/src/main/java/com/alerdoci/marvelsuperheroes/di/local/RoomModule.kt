package com.alerdoci.marvelsuperheroes.di.local

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alerdoci.marvelsuperheroes.data.datasource.cache.database.SuperHeroesDatabase
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants.MarvelConstants.DATABASE_NAME
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.impl.SuperHeroesCacheDataStoreImpl
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.impl.SuperHeroesRemoteDataStoreImpl
import com.alerdoci.marvelsuperheroes.data.datasource.remote.service.MarvelService
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Named("cache")
    @Provides
    fun providesCacheSuperHeroesDataStore(superHeroesDatabase: SuperHeroesDatabase): SuperheroesDataStore = SuperHeroesCacheDataStoreImpl(superHeroesDatabase)

    @Named("remote")
    @Provides
    fun providesRemoteSuperHeroesDataStore(remoteService: MarvelService): SuperheroesDataStore = SuperHeroesRemoteDataStoreImpl(remoteService)

    @Singleton
    @Provides
    fun providesMarvelDatabase(@ApplicationContext context: Context): SuperHeroesDatabase =
        Room.databaseBuilder(context, SuperHeroesDatabase::class.java, DATABASE_NAME)
            //region automigrations
            .addMigrations(*SuperHeroesDatabase.migrations.toTypedArray())
            .enableMultiInstanceInvalidation()
            .setQueryCallback(object : RoomDatabase.QueryCallback {
                override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                    Log.d("SqlQuery", "$sqlQuery, $bindArgs")
                }
            }, Executors.newSingleThreadExecutor())
            //endregion
            .build()
}