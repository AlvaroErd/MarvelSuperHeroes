package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable


data class ModelResult(
    val comics: ModelComics?,
    val description: String?,
    val events: ModelEvents?,
    val id: Int?,
    val name: String?,
    val series: ModelSeries?,
    val imageFinal: String?
) : Serializable