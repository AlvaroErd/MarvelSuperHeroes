package com.alerdoci.marvelsuperheroes.domain.models.features.superheroes

import java.io.Serializable

data class ModelSuperHeroesList(
    val attributionHTML: String?,
    val attributionText: String?,
    val code: Int?,
    val copyright: String?,
    val data: ModelData?,
    val etag: String?,
    val status: String?
) : Serializable