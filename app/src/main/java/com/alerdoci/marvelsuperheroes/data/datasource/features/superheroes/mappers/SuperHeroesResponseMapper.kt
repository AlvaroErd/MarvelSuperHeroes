package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.mappers

import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroes
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroesData
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult.CacheSuperHeroesComics
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult.CacheSuperHeroesEvents
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult.CacheSuperHeroesSeries
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity.CacheSuperHeroesResult.CacheSuperHeroesThumbnail
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteComics
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteData
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteEvents
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteResult
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteSeries
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteSuperHeroes
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models.RemoteThumbnail
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroes
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesComics
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesData
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesEvents
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesSeries
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesThumbnail

/**
domain <= data
https://jsonviewer.stack.hu/
List [ ] .map { it.toDomain() },
Array { } .toDomain(),
 */

//region Remote
fun RemoteSuperHeroes.toDomain(): SuperHeroes = SuperHeroes(
    data = this.data?.toDomain(),
)

fun RemoteData.toDomain(): SuperHeroesData = SuperHeroesData(
    limit = this.limit,
    offset = this.offset,
    results = this.results?.map { it.toDomain() },
)

fun RemoteResult.toDomain(): SuperHeroesResult = SuperHeroesResult(
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

fun RemoteComics.toDomain(): SuperHeroesComics = SuperHeroesComics(
    available = this.available,
)

fun RemoteEvents.toDomain(): SuperHeroesEvents = SuperHeroesEvents(
    available = this.available,
)

fun RemoteSeries.toDomain(): SuperHeroesSeries = SuperHeroesSeries(
    available = this.available,
)

fun RemoteThumbnail.toDomain(): SuperHeroesThumbnail = SuperHeroesThumbnail(
    extension = this.extension,
    path = this.path
)
//endregion

//region Cache
fun CacheSuperHeroes.toDomain(): SuperHeroes = SuperHeroes(
    data = this.data?.toDomain(),
)

fun CacheSuperHeroesData.toDomain(): SuperHeroesData = SuperHeroesData(
    limit = this.limit,
    offset = this.offset,
    results = this.results?.map { it.toDomain() },
)

fun CacheSuperHeroesResult.toDomain(): SuperHeroesResult = SuperHeroesResult(
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

fun CacheSuperHeroesComics.toDomain(): SuperHeroesComics = SuperHeroesComics(
    available = this.available,
)

fun CacheSuperHeroesEvents.toDomain(): SuperHeroesEvents = SuperHeroesEvents(
    available = this.available,
)

fun CacheSuperHeroesSeries.toDomain(): SuperHeroesSeries = SuperHeroesSeries(
    available = this.available,
)

fun CacheSuperHeroesThumbnail.toDomain(): SuperHeroesThumbnail = SuperHeroesThumbnail(
    extension = this.extension,
    path = this.path
)
//endregion

//region Source of truth
fun SuperHeroes.toCache(): CacheSuperHeroes = CacheSuperHeroes(
    data = this.data?.toDomain(),
)

fun SuperHeroesData.toDomain(): CacheSuperHeroesData = CacheSuperHeroesData(
    limit = this.limit,
    offset = this.offset,
    results = this.results?.map { it.toDomain() },
)

fun SuperHeroesResult.toDomain(): CacheSuperHeroesResult = CacheSuperHeroesResult(
    comics = comics?.toDomain(),
    description = description,
    events = events?.toDomain(),
    id = id,
    name = name,
    series = series?.toDomain(),
    thumbnail = null,
    //And Imagefinal????
)

fun SuperHeroesComics.toDomain(): CacheSuperHeroesComics = CacheSuperHeroesComics(
    available = this.available,
)

fun SuperHeroesEvents.toDomain(): CacheSuperHeroesEvents = CacheSuperHeroesEvents(
    available = this.available,
)

fun SuperHeroesSeries.toDomain(): CacheSuperHeroesSeries = CacheSuperHeroesSeries(
    available = this.available,
)

fun SuperHeroesThumbnail.toDomain(): CacheSuperHeroesThumbnail = CacheSuperHeroesThumbnail(
    extension = this.extension,
    path = this.path
)

//endregion