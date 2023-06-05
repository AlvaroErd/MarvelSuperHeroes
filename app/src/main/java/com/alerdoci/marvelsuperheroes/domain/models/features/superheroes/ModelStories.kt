package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable

data class ModelStories(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ModelItemXXX>?,
    val returned: Int?
) : Serializable