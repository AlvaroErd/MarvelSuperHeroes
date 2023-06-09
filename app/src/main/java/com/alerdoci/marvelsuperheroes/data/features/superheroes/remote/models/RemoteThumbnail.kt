package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteThumbnail(
    @SerializedName("extension") val extension: String?,
    @SerializedName("path") val path: String?
)