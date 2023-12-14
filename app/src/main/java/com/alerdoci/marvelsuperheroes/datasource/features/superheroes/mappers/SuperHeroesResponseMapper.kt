package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.mappers

import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models.RemoteResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult

/**
domain <= data
https://jsonviewer.stack.hu/
List [ ] .map { it.toDomain() },
Array { } .toDomain(),
 */

// region Remote
fun RemoteResult.toDomain(): ModelResult = ModelResult(
    comics = comics?.available,
    description = description,
    events = events?.available,
    id = id,
    name = name,
    series = series?.available,
    image = (thumbnail?.path + "." + thumbnail?.extension).replace(
        "http",
        "https"
    )
)
//endregion

//region Cache
fun CacheSuperHeroesResult.toDomain(): ModelResult = ModelResult(
    comics = comics,
    description = description,
    events = events,
    id = id,
    name = name,
    series = series,
    image = image
)

//endregion

//region Source of truth
fun ModelResult.toCache(): CacheSuperHeroesResult = CacheSuperHeroesResult(
    comics = comics,
    description = description,
    events = events,
    id = id,
    name = name,
    series = series,
    image = image
)
