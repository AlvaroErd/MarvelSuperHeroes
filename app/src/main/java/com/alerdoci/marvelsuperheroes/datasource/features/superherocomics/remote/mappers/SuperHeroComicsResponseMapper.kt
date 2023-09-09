package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.mappers

import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsDate
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsThumbnail
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsUrls
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsDate
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsThumbnail
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsUrls
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsDate
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsThumbnail
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsUrls

/**
 * List [ ] .map { it.toDomain() },
Array { } .toDomain()
 */

fun RemoteComicsResult.toDomain(): ModelComicsResult = ModelComicsResult(
    id = this.id,
    title = this.title,
    dates = dates?.map { it.toDomain() },
    thumbnail = thumbnail?.toDomain(),
    imageFinal = (this.thumbnail?.path + "/standard_fantastic" + "." + this.thumbnail?.extension).replace(
        "http",
        "https"
    ),
    urls = urls?.map { it.toDomain() },
)

fun RemoteComicsDate.toDomain(): ModelComicsDate = ModelComicsDate(
    type = this.type,
    date = this.date,
)

fun RemoteComicsUrls.toDomain(): ModelComicsUrls = ModelComicsUrls(
    type = this.type,
    url = this.url,
)

fun RemoteComicsThumbnail.toDomain(): ModelComicsThumbnail = ModelComicsThumbnail(
    extension = this.extension,
    path = this.path
)

//endregion

// region Cache
fun CacheComicsResult.toDomain(): ModelComicsResult = ModelComicsResult(
    id = id,
    title = title,
    dates = dates?.map { it.toDomain() },
    thumbnail = thumbnail?.toDomain(),
    imageFinal = (this.thumbnail?.path + "/standard_fantastic" + "." + this.thumbnail?.extension).replace(
        "http",
        "https"
    ),
    urls = urls?.map { it.toDomain() }
)

fun CacheComicsDate.toDomain(): ModelComicsDate = ModelComicsDate(
    type = this.type,
    date = this.date
)

fun CacheComicsThumbnail.toDomain(): ModelComicsThumbnail = ModelComicsThumbnail(
    path = this.path,
    extension = this.extension

)

fun CacheComicsUrls.toDomain(): ModelComicsUrls = ModelComicsUrls(
    type = this.type,
    url = this.url,
)

//region Source of truth
fun ModelComicsResult.toDomain(): CacheComicsResult = CacheComicsResult(
    id = id,
    title = title,
    dates = dates?.map { it.toDomain() },
    thumbnail = thumbnail?.toDomain(),
    urls = urls?.map { it.toDomain() },
    imageFinal = (this.thumbnail?.path + "/standard_fantastic" + "." + this.thumbnail?.extension).replace(
        "http",
        "https"
    ),
)

fun ModelComicsDate.toDomain(): CacheComicsDate = CacheComicsDate(
    type = this.type,
    date = this.date
)

fun ModelComicsUrls.toDomain(): CacheComicsUrls = CacheComicsUrls(
    type = this.type,
    url = this.url
)

fun ModelComicsThumbnail.toDomain(): CacheComicsThumbnail = CacheComicsThumbnail(
    path = null,
    extension = null
)
