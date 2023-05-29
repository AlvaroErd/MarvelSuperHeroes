package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

data class ModelData(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val results: List<ModelResult>?,
    val total: Int?
)