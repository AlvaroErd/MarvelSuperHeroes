package com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics

import java.io.Serializable

data class ModelComicsData(
    val results: List<ModelComicsResult>?,
) : Serializable