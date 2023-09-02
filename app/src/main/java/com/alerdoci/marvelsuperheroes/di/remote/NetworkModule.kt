package com.alerdoci.marvelsuperheroes.di.remote

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alerdoci.marvelsuperheroes.BuildConfig
import com.alerdoci.marvelsuperheroes.data.constants.BASE_URL
import com.alerdoci.marvelsuperheroes.data.datasource.cache.database.SuperHeroesDatabase
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants.MarvelConstants
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.impl.SuperHeroesCacheDataStoreImpl
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.impl.SuperHeroesRemoteDataStoreImpl
import com.alerdoci.marvelsuperheroes.data.datasource.remote.service.MarvelService
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.SuperHeroesRemoteImplementFactory
import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.factory.SuperHeroesDataFactory
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMarvelRepository(factory: SuperHeroesDataFactory): MarvelRepository = SuperHeroesRemoteImplementFactory(factory)

    @Named("cache")
    @Provides
    fun providesCacheSuperHeroesDataStore(superHeroesDatabase: SuperHeroesDatabase): SuperheroesDataStore =
        SuperHeroesCacheDataStoreImpl(superHeroesDatabase)

    @Named("remote")
    @Provides
    fun providesRemoteSuperHeroesDataStore(remoteService: MarvelService): SuperheroesDataStore =
        SuperHeroesRemoteDataStoreImpl(remoteService)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logginInterceptorCreate())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun logginInterceptorCreate(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    fun getHash(ts: String): String {
        return md5(ts + BuildConfig.API_KEY_PRIVATE + BuildConfig.API_KEY_PUBLIC)
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    @Singleton
    @Provides
    fun provideRetrofit(): MarvelService {
        return Retrofit.Builder()
//            .baseUrl("https://gateway.marvel.com/")
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build().create(MarvelService::class.java)
    }

    @Provides
    @Singleton
    fun providesMarvelDatabase(@ApplicationContext context: Context): SuperHeroesDatabase =
        Room.databaseBuilder(
            context = context,
            klass = SuperHeroesDatabase::class.java,
            name = MarvelConstants.DATABASE_NAME
        )
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
