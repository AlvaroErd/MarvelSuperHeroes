package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteUrl(
    @SerializedName("type") val type: String?,
    @SerializedName("url") val url: String?
)