package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.mappers

import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsDate
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsUrls
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsDate
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsUrls
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsDate
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsUrls

/**
 * List [ ] .map { it.toDomain() },
 * Array { } .toDomain()
 * Dom = Mod
 */

fun RemoteComicsResult.toDomain(): ModelComicsResult = ModelComicsResult(
    id = this.id,
    title = this.title,
    dates = dates?.map { it.toDomain() },
    imageFinal = (this.thumbnail?.path + "/standard_fantastic" + "." + this.thumbnail?.extension).replace(
        "http",
        "https"
    ),
    urls = urls?.map { it.toDomain() }
)

fun RemoteComicsDate.toDomain(): ModelComicsDate = ModelComicsDate(
    type = this.type,
    date = this.date
)

fun RemoteComicsUrls.toDomain(): ModelComicsUrls = ModelComicsUrls(
    type = this.type,
    url = this.url
)
//endregion

// region Cache
fun CacheComicsResult.toDomain(): ModelComicsResult = ModelComicsResult(
    id = id,
    title = title,
    dates = dates?.map { it.toDomain() },
    imageFinal = imageFinal,
    urls = urls?.map { it.toDomain() }
)

fun CacheComicsDate.toDomain(): ModelComicsDate = ModelComicsDate(
    type = this.type,
    date = this.date
)

fun CacheComicsUrls.toDomain(): ModelComicsUrls = ModelComicsUrls(
    type = this.type,
    url = this.url
)

//region Source of truth
fun ModelComicsResult.toDomain(): CacheComicsResult = CacheComicsResult(
    id = id,
    title = title,
    dates = dates?.map { it.toDomain() },
    urls = urls?.map { it.toDomain() },
    imageFinal = imageFinal,
)

fun ModelComicsDate.toDomain(): CacheComicsDate = CacheComicsDate(
    type = this.type,
    date = this.date
)

fun ModelComicsUrls.toDomain(): CacheComicsUrls = CacheComicsUrls(
    type = this.type,
    url = this.url
)
