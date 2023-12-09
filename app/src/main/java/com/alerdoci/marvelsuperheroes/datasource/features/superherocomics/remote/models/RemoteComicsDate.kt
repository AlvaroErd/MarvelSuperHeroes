package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RemoteComicsDate(
    @SerializedName("type")
    val type: String?,
    @SerializedName("date")
    val date: String?
) : Parcelable
