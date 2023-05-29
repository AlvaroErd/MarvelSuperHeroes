package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteItemXXX(
    @SerializedName("name") val name: String?,
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("type") val type: String?
)