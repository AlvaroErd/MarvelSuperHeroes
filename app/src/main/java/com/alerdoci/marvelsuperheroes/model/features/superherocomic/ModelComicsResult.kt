package com.alerdoci.marvelsuperheroes.model.features.superherocomic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.compose.runtime.Immutable

@Immutable
@Parcelize
data class ModelComicsResult(
    val id: Int,
    val superHeroId: Int,
    val title: String?,
    val onSaleDate: String?,
    val image: String?
) : Parcelable
