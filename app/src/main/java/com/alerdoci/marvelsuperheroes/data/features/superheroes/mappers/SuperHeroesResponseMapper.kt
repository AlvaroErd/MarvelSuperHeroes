package com.alerdoci.marvelsuperheroes.data.features.superheroes.mappers

import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteComics
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteData
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteEvents
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteItem
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteItemXXX
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteResult
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteSeries
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteStories
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteSuperHeroesList
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteThumbnail
import com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models.RemoteUrl
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelComics
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelData
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelEvents
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelItem
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelItemXXX
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelSeries
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelStories
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelSuperHeroesList
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelThumbnail
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelUrl

fun RemoteSuperHeroesList.toDomain(): ModelSuperHeroesList = ModelSuperHeroesList(
    // domain <= data
    // https://jsonviewer.stack.hu/
    // List [ ] .map { it.toDomain() },
    // Array { } .toDomain(),

    attributionHTML = this.attributionHTML,
    attributionText = this.attributionText,
    code = this.code,
    copyright = this.copyright,
    data = this.data?.toDomain(),
    etag = this.etag,
    status = this.status,
)

fun RemoteData.toDomain(): ModelData = ModelData(
    count = this.count,
    limit = this.limit,
    offset = this.offset,
    results = this.results?.map { it.toDomain() },
    total = this.total
)

fun RemoteResult.toDomain(): ModelResult = ModelResult(
    comics = this.comics?.toDomain(),
    description = this.description,
    events = this.events?.toDomain(),
    id = this.id,
    modified = this.modified,
    name = this.name,
    resourceURI = this.resourceURI,
    series = this.series?.toDomain(),
    stories = this.stories?.toDomain(),
    thumbnail = this.thumbnail?.toDomain(),
    urls = this.urls?.map { it.toDomain() },
)

fun RemoteComics.toDomain(): ModelComics = ModelComics(
    available = this.available,
    collectionURI = this.collectionURI,
    items = this.items?.map { it?.toDomain() },
    returned = this.returned,
)

fun RemoteItem.toDomain(): ModelItem = ModelItem(
    name = this.name,
    resourceURI = this.resourceURI
)

fun RemoteEvents.toDomain(): ModelEvents = ModelEvents(
    available = this.available,
    collectionURI = this.collectionURI,
    items = this.items?.map { it.toDomain() },
    returned = this.returned
)

fun RemoteSeries.toDomain(): ModelSeries = ModelSeries(
    available = this.available,
    collectionURI = this.collectionURI,
    items = this.items?.map { it.toDomain() },
    returned = this.returned
)

fun RemoteStories.toDomain(): ModelStories = ModelStories(
    available = null,
    collectionURI = null,
    items = this.items?.map { it.toDomain() },
    returned = null
)

fun RemoteItemXXX.toDomain(): ModelItemXXX = ModelItemXXX(
    name = this.name,
    resourceURI = this.resourceURI,
    type = this.type
)

fun RemoteThumbnail.toDomain(): ModelThumbnail = ModelThumbnail(
    extension = this.extension,
    path = this.path
)

fun RemoteUrl.toDomain(): ModelUrl = ModelUrl(
    type = this.type,
    url = this.url
)