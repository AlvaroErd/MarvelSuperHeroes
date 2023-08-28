package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models

import com.google.gson.annotations.SerializedName

data class RemoteSuperHeroes(
    @SerializedName("data") val data: RemoteData?,
)

data class RemoteData(
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("results") val results: List<RemoteResult>?,
)

data class RemoteResult(
    @SerializedName("comics") val comics: RemoteComics?,
    @SerializedName("description") val description: String?,
    @SerializedName("events") val events: RemoteEvents?,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("series") val series: RemoteSeries?,
    @SerializedName("thumbnail") val thumbnail: RemoteThumbnail?,
)

data class RemoteComics(
    @SerializedName("available") val available: Int?,
)

data class RemoteEvents(
    @SerializedName("available") val available: Int?,
)

data class RemoteSeries(
    @SerializedName("available") val available: Int?,
)

data class RemoteThumbnail(
    @SerializedName("extension") val extension: String?,
    @SerializedName("path") val path: String?
)