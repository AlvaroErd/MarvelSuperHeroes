package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable

data class ModelData(
    val limit: Int?,
    val offset: Int?,
    val results: List<ModelResult>?,
) : Serializable