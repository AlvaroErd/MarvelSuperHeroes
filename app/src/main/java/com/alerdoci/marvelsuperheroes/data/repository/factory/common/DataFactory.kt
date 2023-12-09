package com.alerdoci.marvelsuperheroes.data.repository.factory.common

interface DataFactory<C, R> {

    val cacheDataStore: C
    val remoteDataStore: R
}