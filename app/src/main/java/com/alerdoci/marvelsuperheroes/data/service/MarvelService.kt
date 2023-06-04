package com.alerdoci.marvelsuperheroes.data.service

import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteSuperHeroesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("v1/public/characters")
    suspend fun getMarvelSuperHeroes(
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<RemoteSuperHeroesList>

    @GET("v1/public/characters/{id}")
    suspend fun getMarvelSuperHero(
        @Path("id") superHeroId: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<RemoteSuperHeroesList>
}
