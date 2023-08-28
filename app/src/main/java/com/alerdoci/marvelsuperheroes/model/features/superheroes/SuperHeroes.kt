package com.alerdoci.marvelsuperheroes.model.features.superheroes

data class SuperHeroes(
    val data: SuperHeroesData?,
)

data class SuperHeroesData(
    val limit: Int?,
    val offset: Int?,
    val results: List<SuperHeroesResult>?,
)

data class SuperHeroesResult(
    val comics: SuperHeroesComics?,
    val description: String?,
    val events: SuperHeroesEvents?,
    val id: Int,
    val name: String?,
    val series: SuperHeroesSeries?,
    val imageFinal: String?
)

data class SuperHeroesComics(
    val available: Int?,
)

data class SuperHeroesEvents(
    val available: Int?,
)

data class SuperHeroesSeries(
    val available: Int?,
)

data class SuperHeroesThumbnail(
    val extension: String?,
    val path: String?
)