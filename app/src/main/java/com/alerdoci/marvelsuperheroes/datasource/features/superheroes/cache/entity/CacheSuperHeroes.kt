package com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.constants.MarvelConstants.SUPERHEROES_TABLE_NAME

@Entity(tableName = SUPERHEROES_TABLE_NAME)
data class CacheSuperHeroesResult(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "comics")
    val comics: Int?,
    @ColumnInfo(name = "series")
    val series: Int?,
    @ColumnInfo(name = "events")
    val events: Int?,
    @ColumnInfo(name = "imageFinal")
    val imageFinal: String?,
)
