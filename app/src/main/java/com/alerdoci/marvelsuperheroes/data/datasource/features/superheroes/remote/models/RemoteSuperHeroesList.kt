package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteSuperHeroesList(
    @SerializedName("data") val data: RemoteData?,
)
