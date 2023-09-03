package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComics(
    @SerializedName("available") val available: Int?,
)
