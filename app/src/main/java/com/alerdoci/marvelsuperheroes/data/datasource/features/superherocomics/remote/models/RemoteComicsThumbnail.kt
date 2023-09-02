package com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComicsThumbnail(
    @SerializedName("path")
    val path: String?,
    @SerializedName("extension")
    val extension: String?
)