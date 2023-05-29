package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteStories(
    @SerializedName("available") val available: Int?,
    @SerializedName("collectionURI") val collectionURI: String?,
    @SerializedName("items") val items: List<RemoteItemXXX>?,
    @SerializedName("returned") val returned: Int?
)