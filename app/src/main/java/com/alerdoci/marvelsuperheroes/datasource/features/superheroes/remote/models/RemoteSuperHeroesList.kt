package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RemoteSuperHeroesList(
    @SerializedName("data") val data: RemoteData?,
)
