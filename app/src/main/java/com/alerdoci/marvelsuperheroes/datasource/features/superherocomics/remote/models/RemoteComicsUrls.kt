package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComicsUrls(
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)
