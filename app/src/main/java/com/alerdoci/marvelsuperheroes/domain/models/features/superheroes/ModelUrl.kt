package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable

data class ModelUrl(
    val type: String?,
    val url: String?
) : Serializable