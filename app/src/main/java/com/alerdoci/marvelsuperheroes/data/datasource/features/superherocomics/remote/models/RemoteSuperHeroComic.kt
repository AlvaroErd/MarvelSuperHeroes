package com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models

import com.google.gson.annotations.SerializedName

data class RemoteSuperHeroComic(
    @SerializedName("attributionText")
    val attributionText: String?,
    @SerializedName("data")
    val data: RemoteSuperHeroComicData?
)

data class RemoteSuperHeroComicData(
    @SerializedName("results") val results: List<RemoteSuperHeroComicResult>?,
)

data class RemoteSuperHeroComicResult(
    @SerializedName("title")
    val title: String?,
    @SerializedName("dates")
    val dates: List<RemoteSuperHeroComicDate>?,
    @SerializedName("thumbnail")
    val thumbnail: RemoteSuperHeroComicThumbnail?,
    @SerializedName("urls")
    val urls: List<RemoteSuperHeroComicUrls>?,
)

data class RemoteSuperHeroComicDate(
    @SerializedName("type")
    val type: String?,
    @SerializedName("date")
    val date: String?
)

data class RemoteSuperHeroComicThumbnail(
    @SerializedName("path")
    val path: String?,
    @SerializedName("extension")
    val extension: String?
)

data class RemoteSuperHeroComicUrls(
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)