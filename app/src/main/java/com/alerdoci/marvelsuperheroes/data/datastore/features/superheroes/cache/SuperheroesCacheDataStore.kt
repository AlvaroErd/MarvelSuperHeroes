package com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.cache

import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult

interface SuperheroesCacheDataStore : SuperheroesDataStore {
    suspend fun insertOrUpdateSuperHeroes(superHeroesList: List<ModelResult>)
    suspend fun insertOrUpdateSuperHeroesComic(superHeroesComicList: List<ModelComicsResult>)
}
