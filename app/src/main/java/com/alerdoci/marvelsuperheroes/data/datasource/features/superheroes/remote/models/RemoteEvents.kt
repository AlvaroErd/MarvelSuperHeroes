package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteEvents(
    @SerializedName("available") val available: Int?,
)
