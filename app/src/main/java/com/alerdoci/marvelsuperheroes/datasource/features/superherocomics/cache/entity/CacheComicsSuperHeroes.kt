package com.alerdoci.marvelsuperheroes.datasource.features.superherocomics.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.alerdoci.marvelsuperheroes.datasource.features.superheroes.cache.constants.MarvelConstants.SUPERHEROES_COMICS_TABLE_NAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = SUPERHEROES_COMICS_TABLE_NAME)
data class CacheComicsResult(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "onSaleDate")
    val onSaleDate: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "image")
    val image: String?,
)
