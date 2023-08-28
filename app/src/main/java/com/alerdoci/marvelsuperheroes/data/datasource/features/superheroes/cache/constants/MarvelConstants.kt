package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants

object MarvelConstants {

    const val TABLE_NAME = "superheroes_table"
    const val DATABASE_NAME = "marvel_superheroes.db"
    const val QUERY_MARVEL = "SELECT * FROM $TABLE_NAME"
    const val DELETE_ALL_MARVEL = "DELETE FROM $TABLE_NAME"
}