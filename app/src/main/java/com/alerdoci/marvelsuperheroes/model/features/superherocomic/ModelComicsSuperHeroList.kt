package com.alerdoci.marvelsuperheroes.model.features.superherocomic

import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsData
import java.io.Serializable

data class ModelComicsSuperHeroList(
    val attributionText: String?,
    val data: ModelComicsData?
) : Serializable
