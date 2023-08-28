package com.alerdoci.marvelsuperheroes.data.datasource.remote.service

import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models.RemoteSuperHeroComic
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteSuperHeroes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("v1/public/characters")
    suspend fun getMarvelSuperHeroes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
    ): Response<RemoteSuperHeroes>

    @GET("v1/public/characters")
    suspend fun getMarvelSuperHeroesSearched(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("nameStartsWith") nameSearched: String? = null,
    ): Response<RemoteSuperHeroes>

    @GET("v1/public/characters/{id}")
    suspend fun getMarvelSuperHero(
        @Path("id") superHeroId: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<RemoteSuperHeroes>

    @GET("v1/public/characters/{id}/comics")
    suspend fun getMarvelSuperHeroComics(
        @Path("id") superHeroId: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<RemoteSuperHeroComic>
}
