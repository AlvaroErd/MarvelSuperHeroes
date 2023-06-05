package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable

data class ModelEvents(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ModelItem>?,
    val returned: Int?
) : Serializable