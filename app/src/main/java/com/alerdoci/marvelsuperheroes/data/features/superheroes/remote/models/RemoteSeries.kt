package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteSeries(
    @SerializedName("available") val available: Int?,
)