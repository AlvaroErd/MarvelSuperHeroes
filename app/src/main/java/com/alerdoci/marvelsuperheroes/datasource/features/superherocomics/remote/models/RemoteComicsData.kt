package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComicsData(
    @SerializedName("results") val results: List<RemoteComicsResult>?,
)
