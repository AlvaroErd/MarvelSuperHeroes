package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComicsResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("dates")
    val dates: List<RemoteComicsDate>?,
    @SerializedName("thumbnail")
    val thumbnail: RemoteComicsThumbnail?,
)
