package com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.factory

import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.cache.SuperheroesCacheDataStore
import com.alerdoci.marvelsuperheroes.data.datastore.features.superheroes.SuperheroesDataStore
import com.alerdoci.marvelsuperheroes.data.repository.factory.common.DataFactory
import javax.inject.Inject
import javax.inject.Named

class SuperHeroesDataFactory @Inject constructor(
    @Named("cache") override val cacheDataStore: SuperheroesCacheDataStore,
    @Named("remote") override val remoteDataStore: SuperheroesDataStore
) : DataFactory<SuperheroesDataStore, SuperheroesDataStore>
