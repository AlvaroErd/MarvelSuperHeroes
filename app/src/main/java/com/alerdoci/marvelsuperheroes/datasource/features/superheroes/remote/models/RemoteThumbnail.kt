package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RemoteThumbnail(
    @SerializedName("extension") val extension: String?,
    @SerializedName("path") val path: String?
) : Parcelable
