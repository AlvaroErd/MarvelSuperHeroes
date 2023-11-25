package com.alerdoci.marvelsuperheroes.model.features.superherocomic

import java.io.Serializable

data class ModelComicsResult(
    val id: Int,
    val title: String?,
    val onSaleDate: String?,
    val image: String?
) : Serializable
