package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

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
    val thumbnail: ModelThumbnail?,
    val urls: List<ModelUrl>?
)