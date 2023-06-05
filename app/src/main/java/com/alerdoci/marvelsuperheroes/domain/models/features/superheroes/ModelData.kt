package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable

data class ModelData(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val results: List<ModelResult>?,
    val total: Int?
) : Serializable