package com.alerdoci.marvelsuperheroes.domain.models.features.superherocomics

import java.io.Serializable

data class ModelComicsSuperHeroList(
    val attributionText: String?,
    val data: ModelComicsData?
) : Serializable