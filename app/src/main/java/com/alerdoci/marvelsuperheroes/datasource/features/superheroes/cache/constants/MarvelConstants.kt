package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.constants

object MarvelConstants {

    const val DATABASE_NAME = "marvel_superheroes.db"

    const val SUPERHEROES_TABLE_NAME = "superheroes_table"
    const val QUERY_SUPERHEROES_ORDER_BY_NAME = "SELECT * FROM $SUPERHEROES_TABLE_NAME ORDER BY name ASC"

    const val SUPERHEROES_COMICS_TABLE_NAME = "superheroes_comics_table"

    const val QUERY_SUPERHEROES = "SELECT * FROM $SUPERHEROES_TABLE_NAME"
    const val QUERY_SUPERHEROES_COMICS = "SELECT * FROM $SUPERHEROES_COMICS_TABLE_NAME"

    const val DELETE_ALL_MARVEL = "DELETE FROM "
}
