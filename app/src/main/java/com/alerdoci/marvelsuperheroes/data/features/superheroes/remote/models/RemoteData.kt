package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteData(
    @SerializedName("count") val count: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("results") val results: List<RemoteResult>?,
    @SerializedName("total") val total: Int?
)