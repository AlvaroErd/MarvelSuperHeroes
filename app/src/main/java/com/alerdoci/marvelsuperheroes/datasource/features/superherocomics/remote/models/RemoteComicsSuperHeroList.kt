package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RemoteComicsSuperHeroList(
    @SerializedName("data") val data: RemoteComicsData?
)
