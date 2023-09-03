package com.alerdoci.marvelsuperheroes.data.features.superherocomics.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComicsDate(
    @SerializedName("type")
    val type: String?,
    @SerializedName("date")
    val date: String?
)