package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.mappers

import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.replaceHttp
import com.alerdoci.marvelsuperheroes.app.common.utils.toStringFormatted
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsResult
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.DOT
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * List [ ] .map { it.toDomain() },
 * Array { } .toDomain()
 * Dom = Mod
 */

fun RemoteComicsResult.toDomain(superHeroId: Int): ModelComicsResult {
    val remoteDate = dates?.get(0)?.date
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val parsedDate = remoteDate?.let { dateFormat.parse(it) }
    val formattedDate = parsedDate?.toStringFormatted()

    return ModelComicsResult(
        id = id,
        title = title,
        onSaleDate = formattedDate ?: "?",
        image = (this.thumbnail?.path + DOT + this.thumbnail?.extension).replaceHttp(),
        superHeroId = superHeroId
    )
}

// region Cache
fun CacheComicsResult.toDomain(superHeroId: Int): ModelComicsResult = ModelComicsResult(
    id = id,
    title = title,
    onSaleDate = onSaleDate,
    image = image,
    superHeroId = superHeroId
)

//region Source of truth
fun ModelComicsResult.toDomain(): CacheComicsResult = CacheComicsResult(
    id = id,
    title = title,
    onSaleDate = onSaleDate,
    image = image,
    superHeroId = superHeroId
)
