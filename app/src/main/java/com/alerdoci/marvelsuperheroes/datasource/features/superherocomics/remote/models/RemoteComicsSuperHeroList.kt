package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComicsSuperHeroList(
    @SerializedName("attributionText")
    val attributionText: String?,
    @SerializedName("data")
    val data: RemoteComicsData?
)
