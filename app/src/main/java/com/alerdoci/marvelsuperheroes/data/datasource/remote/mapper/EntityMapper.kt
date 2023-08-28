package com.alerdoci.marvelsuperheroes.data.datasource.remote.mapper

interface EntityMapper<in M, out E> {

    fun mapFromRemote(type: M): E
}