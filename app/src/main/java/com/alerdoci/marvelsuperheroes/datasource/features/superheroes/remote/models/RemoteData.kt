package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteData(
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("results") val results: List<RemoteResult>?,
)
