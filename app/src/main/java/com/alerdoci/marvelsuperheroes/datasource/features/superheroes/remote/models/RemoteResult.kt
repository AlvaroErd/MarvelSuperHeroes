package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RemoteResult(
    @SerializedName("comics")
    val comics: RemoteComics?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("events")
    val events: RemoteEvents?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("series")
    val series: RemoteSeries?,
    @SerializedName("thumbnail")
    val thumbnail: RemoteThumbnail?,
) : Parcelable
