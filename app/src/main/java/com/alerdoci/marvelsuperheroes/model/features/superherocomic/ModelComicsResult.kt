package com.alerdoci.marvelsuperheroes.model.features.superherocomic

import java.io.Serializable

data class ModelComicsResult(
    val id: Int,
    val title: String?,
    val dates: List<ModelComicsDate>?,
    val thumbnail: ModelComicsThumbnail?,
    val urls: List<ModelComicsUrls>?,
    val imageFinal: String?
) : Serializable
