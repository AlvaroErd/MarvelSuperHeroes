package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

data class ModelSeries(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ModelItem>?,
    val returned: Int?
)