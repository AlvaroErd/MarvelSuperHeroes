package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.constants.MarvelConstants.SUPERHEROES_COMICS_TABLE_NAME

@Immutable
@Entity(tableName = SUPERHEROES_COMICS_TABLE_NAME)
data class CacheComicsResult(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "superHeroId")
    val superHeroId: Int,
    @ColumnInfo(name = "onSaleDate")
    val onSaleDate: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "image")
    val image: String?,
)
