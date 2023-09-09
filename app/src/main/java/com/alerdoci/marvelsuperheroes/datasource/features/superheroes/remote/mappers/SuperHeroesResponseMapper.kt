package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.mappers

import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesComics
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesEvents
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesSeries
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity.CacheSuperHeroesThumbnail
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models.RemoteComics
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models.RemoteEvents
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models.RemoteResult
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models.RemoteSeries
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models.RemoteThumbnail
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelComics
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelEvents
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelSeries
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelThumbnail

/**
domain <= data
https://jsonviewer.stack.hu/
List [ ] .map { it.toDomain() },
Array { } .toDomain(),
 */

// region Remote
fun RemoteResult.toDomain(): ModelResult = ModelResult(
    comics = comics?.toDomain(),
    description = description,
    events = events?.toDomain(),
    id = id,
    name = name,
    series = series?.toDomain(),
    imageFinal = (thumbnail?.path + "/standard_fantastic" + "." + thumbnail?.extension).replace(
        "http",
        "https"
    ),
//    thumbnail = thumbnail?.toDomain()
)

fun RemoteComics.toDomain(): ModelComics = ModelComics(
    available = this.available,
)

fun RemoteEvents.toDomain(): ModelEvents = ModelEvents(
    available = this.available,
)

fun RemoteSeries.toDomain(): ModelSeries = ModelSeries(
    available = this.available,
)

fun RemoteThumbnail.toDomain(): ModelThumbnail = ModelThumbnail(
    extension = this.extension,
    path = this.path
)
//endregion

//region Cache
fun CacheSuperHeroesResult.toDomain(): ModelResult = ModelResult(
    comics = comics?.toDomain(),
    description = description,
    events = events?.toDomain(),
    id = id,
    name = name,
    series = series?.toDomain(),
    imageFinal = (thumbnail?.path + "/standard_fantastic" + "." + thumbnail?.extension).replace(
        "http",
        "https"
    )
)

fun CacheSuperHeroesComics.toDomain(): ModelComics = ModelComics(
    available = this.available,
)

fun CacheSuperHeroesEvents.toDomain(): ModelEvents = ModelEvents(
    available = this.available,
)

fun CacheSuperHeroesSeries.toDomain(): ModelSeries = ModelSeries(
    available = this.available,
)

fun CacheSuperHeroesThumbnail.toDomain(): ModelThumbnail = ModelThumbnail(
    extension = this.extension,
    path = this.path
)
//endregion

//region Source of truth
fun ModelResult.toDomain(): CacheSuperHeroesResult = CacheSuperHeroesResult(
    comics = comics?.toDomain(),
    description = description,
    events = events?.toDomain(),
    id = id,
    name = name,
    series = series?.toDomain(),
    thumbnail = null,
)

fun ModelComics.toDomain(): CacheSuperHeroesComics =
    CacheSuperHeroesComics(
        available = this.available,
    )

fun ModelEvents.toDomain(): CacheSuperHeroesEvents =
    CacheSuperHeroesEvents(
        available = this.available,
    )

fun ModelSeries.toDomain(): CacheSuperHeroesSeries =
    CacheSuperHeroesSeries(
        available = this.available,
    )

fun ModelThumbnail.toDomain(): CacheSuperHeroesThumbnail =
    CacheSuperHeroesThumbnail(
        extension = this.extension,
        path = this.path
    )

//endregion

//override fun toDomain(): ModelResult {
//    throw IllegalMappingException("${this.javaClass.simpleName} can't be mapped to Domain Entity")
//}
