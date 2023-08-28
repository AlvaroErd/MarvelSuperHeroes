package com.alerdoci.marvelsuperheroes.model.features.superherocomic

data class SuperHeroComic(
    val attributionText: String?,
    val data: SuperHeroComicData?
)

data class SuperHeroComicData(
    val results: List<SuperHeroesComicResult>?,
)

data class SuperHeroesComicDate(
    val type: String?,
    val date: String?
)

data class SuperHeroesComicResult(
    val title: String?,
    val dates: List<SuperHeroesComicDate>?,
    val thumbnail: SuperHeroesComicThumbnail?,
    val imageFinal: String?,
    val urls: List<SuperHeroesComicUrls>?
)

data class SuperHeroesComicThumbnail(
    val path: String?,
    val extension: String?
)

data class SuperHeroesComicUrls(
    val type: String?,
    val url: String?
)

// : Serializable