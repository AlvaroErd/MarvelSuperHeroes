package com.alerdoci.marvelsuperheroes.data.features.superheroes.mappers

import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteComics
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteData
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteEvents
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteResult
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteSeries
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteSuperHeroesList
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteThumbnail
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelComics
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelData
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelEvents
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelSeries
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelSuperHeroesList
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelThumbnail

fun RemoteSuperHeroesList.toDomain(): ModelSuperHeroesList = ModelSuperHeroesList(
    // domain <= data
    // https://jsonviewer.stack.hu/
    // List [ ] .map { it.toDomain() },
    // Array { } .toDomain(),

    data = this.data?.toDomain(),
)

fun RemoteData.toDomain(): ModelData = ModelData(
    limit = this.limit,
    offset = this.offset,
    results = this.results?.map { it.toDomain() },
)

fun RemoteResult.toDomain(): ModelResult = ModelResult(
    comics = this.comics?.toDomain(),
    description = this.description,
    events = this.events?.toDomain(),
    id = this.id,
    name = this.name,
    series = this.series?.toDomain(),
    imageFinal = (this.thumbnail?.path + "/standard_fantastic" + "." + this.thumbnail?.extension).replace(
        "http",
        "https"
    )
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