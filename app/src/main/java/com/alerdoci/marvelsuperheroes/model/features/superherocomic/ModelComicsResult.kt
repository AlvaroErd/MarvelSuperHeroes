package com.alerdoci.marvelsuperheroes.model.features.superherocomic

import java.io.Serializable

data class ModelComicsResult(
    val title: String?,
    val dates: List<ModelComicsDate>?,
    val thumbnail: ModelComicsThumbnail?,
    val imageFinal: String?,
    val urls: List<ModelComicsUrls>?
) : Serializable
