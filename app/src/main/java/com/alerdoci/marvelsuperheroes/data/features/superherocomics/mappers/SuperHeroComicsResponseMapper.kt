package com.alerdoci.marvelsuperheroes.data.features.superherocomics.mappers

import com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models.RemoteComicsData
import com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models.RemoteComicsDate
import com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models.RemoteComicsResult
import com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models.RemoteComicsSuperHeroList
import com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models.RemoteComicsThumbnail
import com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models.RemoteComicsUrls
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsData
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsDate
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsResult
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsSuperHeroList
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsThumbnail
import com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics.ModelComicsUrls

fun RemoteComicsSuperHeroList.toDomain(): ModelComicsSuperHeroList = ModelComicsSuperHeroList(
    attributionText = this.attributionText,
    data = data?.toDomain(),
)

fun RemoteComicsData.toDomain(): ModelComicsData = ModelComicsData(
    results = this.results?.map { it.toDomain() },
)

fun RemoteComicsResult.toDomain(): ModelComicsResult = ModelComicsResult(
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