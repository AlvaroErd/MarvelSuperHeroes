package com.alerdoci.marvelsuperheroes.data.features.superheroes.remote.models


import com.google.gson.annotations.SerializedName

data class RemoteSuperHeroesList(
    @SerializedName("attributionHTML") val attributionHTML: String?,
    @SerializedName("attributionText") val attributionText: String?,
    @SerializedName("code") val code: Int?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("data") val data: RemoteData?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("status") val status: String?
)