package com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics

import java.io.Serializable

data class ModelComicsThumbnail(
    val path: String?,
    val extension: String?
) : Serializable