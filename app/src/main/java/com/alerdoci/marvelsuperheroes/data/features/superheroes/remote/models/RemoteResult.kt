package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteResult(
    @SerializedName("comics") val comics: RemoteComics?,
    @SerializedName("description") val description: String?,
    @SerializedName("events") val events: RemoteEvents?,
    @SerializedName("id") val id: Int?,
    @SerializedName("modified") val modified: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("series") val series: RemoteSeries?,
    @SerializedName("stories") val stories: RemoteStories?,
    @SerializedName("thumbnail") val thumbnail: RemoteThumbnail?,
    @SerializedName("urls") val urls: List<RemoteUrl>?
)