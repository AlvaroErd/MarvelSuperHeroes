package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteSeries(
    @SerializedName("available") val available: Int?,
)
