package com.alerdoci.marvelsuperheroes.model.features.superheroes

data class ModelResult(
    val comics: ModelComics?,
    val description: String?,
    val events: ModelEvents?,
    val id: Int,
    val name: String?,
    val series: ModelSeries?,
    val imageFinal: String?
)
