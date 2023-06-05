package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable

data class ModelItem(
    val name: String?,
    val resourceURI: String?
) : Serializable