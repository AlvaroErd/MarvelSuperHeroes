package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable


data class ModelResult(
    val comics: ModelComics?,
    val description: String?,
    val events: ModelEvents?,
    val id: Int?,
    val modified: String?,
    val name: String?,
    val resourceURI: String?,
    val series: ModelSeries?,
    val stories: ModelStories?,
    val urls: List<ModelUrl>?,
    val imageFinal: String?
) : Serializable