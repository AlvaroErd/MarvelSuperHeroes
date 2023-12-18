package com.alerdoci.marvelsuperheroes.model.features.superherocomic.mock

import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult

//Mock
val marvelSuperHeroComicMock1 = ModelComicsResult(
    id= 1,
    title= "Cap 1",
    onSaleDate= "1-1-1",
    image = "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
    superHeroId = 1
)

val marvelSuperHeroComicMock2 = ModelComicsResult(
    id= 1,
    title= "Cap 2",
    onSaleDate= "2-2-2",
    image = "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
    superHeroId = 2
)

val marvelSuperHeroComicMock3 = ModelComicsResult(
    id= 1,
    title= "Cap 3",
    onSaleDate= "3-3-4",
    image = "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
    superHeroId = 3
)

var marvelSuperHeroesComicMock = listOf(
    marvelSuperHeroComicMock1,
    marvelSuperHeroComicMock2,
    marvelSuperHeroComicMock3,
)
