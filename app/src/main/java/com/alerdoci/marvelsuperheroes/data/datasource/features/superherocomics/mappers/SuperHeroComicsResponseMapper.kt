package com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.mappers

import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models.RemoteSuperHeroComic
import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models.RemoteSuperHeroComicData
import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models.RemoteSuperHeroComicDate
import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models.RemoteSuperHeroComicResult
import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models.RemoteSuperHeroComicThumbnail
import com.alerdoci.marvelsuperheroes.data.datasource.features.superherocomics.remote.models.RemoteSuperHeroComicUrls
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroComic
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroComicData
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroesComicDate
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroesComicResult
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroesComicThumbnail
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.SuperHeroesComicUrls

//ToDo add cache and general
fun RemoteSuperHeroComic.toDomain(): SuperHeroComic = SuperHeroComic(
    attributionText = this.attributionText,
    data = data?.toDomain(),
)

fun RemoteSuperHeroComicData.toDomain(): SuperHeroComicData = SuperHeroComicData(
    results = this.results?.map { it.toDomain() },
)

fun RemoteSuperHeroComicResult.toDomain(): SuperHeroesComicResult = SuperHeroesComicResult(
    title = this.title,
    dates = dates?.map { it.toDomain() },
    thumbnail = thumbnail?.toDomain(),
    imageFinal = (this.thumbnail?.path + "/standard_fantastic" + "." + this.thumbnail?.extension).replace(
        "http",
        "https"
    ),
    urls = urls?.map { it.toDomain() },
)

fun RemoteSuperHeroComicDate.toDomain(): SuperHeroesComicDate = SuperHeroesComicDate(
    type = this.type,
    date = this.date,
)

fun RemoteSuperHeroComicUrls.toDomain(): SuperHeroesComicUrls = SuperHeroesComicUrls(
    type = this.type,
    url = this.url,
)

fun RemoteSuperHeroComicThumbnail.toDomain(): SuperHeroesComicThumbnail = SuperHeroesComicThumbnail(
    extension = this.extension,
    path = this.path
)