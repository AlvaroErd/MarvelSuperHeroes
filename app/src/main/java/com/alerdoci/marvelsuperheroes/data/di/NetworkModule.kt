package com.alerdoci.marvelsuperheroes.data.di

import com.alerdoci.marvelsuperheroes.BuildConfig
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.implement.MarvelRepositoryImplement
import com.alerdoci.marvelsuperheroes.data.service.MarvelService
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): MarvelService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build().create(MarvelService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMarvelRepository(remoteService: MarvelService): MarvelRepository {
        return MarvelRepositoryImplement(remoteService)
    }

    fun getHash(ts: String): String {
        return md5(ts + BuildConfig.API_KEY_PRIVATE + BuildConfig.API_KEY_PUBLIC)
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}