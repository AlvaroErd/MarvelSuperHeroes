package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.mappers

import com.alerdoci.marvelsuperheroes.app.common.utils.toStringFormatted
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity.CacheComicsResult
import com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models.RemoteComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * List [ ] .map { it.toDomain() },
 * Array { } .toDomain()
 * Dom = Mod
 */

fun RemoteComicsResult.toDomain(): ModelComicsResult {
    val remoteDate = dates?.get(0)?.date
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val parsedDate = remoteDate?.let { dateFormat.parse(it) }
    val formattedDate = parsedDate?.toStringFormatted()

    return ModelComicsResult(
        id = id,
        title = title,
        onSaleDate = formattedDate ?: "?",
        image = (this.thumbnail?.path + "/standard_fantastic" + "." + this.thumbnail?.extension)
            .replace("http", "https")
    )
}

// region Cache
fun CacheComicsResult.toDomain(): ModelComicsResult = ModelComicsResult(
    id = id,
    title = title,
    onSaleDate = onSaleDate,
    image = image,
)

//region Source of truth
fun ModelComicsResult.toDomain(): CacheComicsResult = CacheComicsResult(
    id = id,
    title = title,
    onSaleDate = onSaleDate,
    image = image,
)
