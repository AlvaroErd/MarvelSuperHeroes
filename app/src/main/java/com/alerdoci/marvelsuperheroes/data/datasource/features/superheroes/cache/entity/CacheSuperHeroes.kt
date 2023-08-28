package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants.MarvelConstants

@Entity(tableName = MarvelConstants.TABLE_NAME)
data class CacheSuperHeroes(
    @PrimaryKey(autoGenerate = false)
    val data: CacheSuperHeroesData?,
)

data class CacheSuperHeroesData(
    val offset: Int?,
    val limit: Int?,
    val results: List<CacheSuperHeroesResult>?,
)

data class CacheSuperHeroesResult(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: CacheSuperHeroesThumbnail?,
    @ColumnInfo(name = "comics")
    val comics: CacheSuperHeroesComics?,
    @ColumnInfo(name = "series")
    val series: CacheSuperHeroesSeries?,
    @ColumnInfo(name = "events")
    val events: CacheSuperHeroesEvents?,
) {

    data class CacheSuperHeroesThumbnail(
        @ColumnInfo(name = "path")
        val path: String?,
        @ColumnInfo(name = "extension")
        val extension: String?
    )

    data class CacheSuperHeroesComics(
        @ColumnInfo(name = "available")
        val available: Int?,
    )

    data class CacheSuperHeroesSeries(
        @ColumnInfo(name = "available")
        val available: Int?,
    )

    data class CacheSuperHeroesEvents(
        @ColumnInfo(name = "available")
        val available: Int?,
    )
}