package com.alerdoci.marvelsuperheroes.model.features.superheroes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelResult(
    val comics: Int?,
    val description: String?,
    val events: Int?,
    val id: Int,
    val name: String?,
    val series: Int?,
    val image: String?
) : Parcelable
