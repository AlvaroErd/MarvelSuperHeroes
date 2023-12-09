package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RemoteComicsResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("dates")
    val dates: List<RemoteComicsDate>?,
    @SerializedName("thumbnail")
    val thumbnail: RemoteComicsThumbnail?,
) : Parcelable
