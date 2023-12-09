package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RemoteData(
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("results") val results: List<RemoteResult>?,
) : Parcelable
