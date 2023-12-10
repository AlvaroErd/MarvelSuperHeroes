package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RemoteComicsThumbnail(
    @SerializedName("path")
    val path: String?,
    @SerializedName("extension")
    val extension: String?
) : Parcelable
