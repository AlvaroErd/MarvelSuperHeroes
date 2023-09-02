package com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComicsResult(
    @SerializedName("title")
    val title: String?,
    @SerializedName("dates")
    val dates: List<RemoteComicsDate>?,
    @SerializedName("thumbnail")
    val thumbnail: RemoteComicsThumbnail?,
    @SerializedName("urls")
    val urls: List<RemoteComicsUrls>?,
)