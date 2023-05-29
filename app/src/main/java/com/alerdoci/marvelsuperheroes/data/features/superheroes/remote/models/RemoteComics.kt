package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteComics(
    @SerializedName("available") val available: Int?,
    @SerializedName("collectionURI") val collectionURI: String?,
    @SerializedName("items") val items: List<RemoteItem?>?,
    @SerializedName("returned") val returned: Int?
)