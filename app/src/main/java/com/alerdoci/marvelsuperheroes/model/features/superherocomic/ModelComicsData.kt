package com.alerdoci.marvelsuperheroes.model.features.superherocomic

import java.io.Serializable

data class ModelComicsData(
    val results: List<ModelComicsResult>?,
) : Serializable
