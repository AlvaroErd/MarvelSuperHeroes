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
    @ColumnInfo(name = "dates")
    val dates: List<CacheComicsDate>?,
    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "thumbnailPath")
    val thumbnailPath: String?,
    @ColumnInfo(name = "thumbnailExtension")
    val thumbnailExtension: String?,

    @ColumnInfo(name = "urls")
    val urls: List<CacheComicsUrls>?,
    @ColumnInfo(name = "imageFinal")
    val imageFinal: String,
)

data class CacheComicsDate(
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "date")
    val date: String?
)

data class CacheComicsThumbnail(
    @ColumnInfo(name = "path")
    val path: String?,
    @ColumnInfo(name = "extension")
    val extension: String?,
)

data class CacheComicsUrls(
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "url")
    val url: String?,
)

class CacheComicsDateListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCacheComicsDateList(dates: List<CacheComicsDate>?): String {
        return gson.toJson(dates)
    }

    @TypeConverter
    fun toCacheComicsDateList(datesString: String): List<CacheComicsDate>? {
        val type = object : TypeToken<List<CacheComicsDate>>() {}.type
        return gson.fromJson(datesString, type)
    }
}

class CacheComicsUrlsListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCacheComicsUrlsList(urls: List<CacheComicsUrls>?): String {
        return gson.toJson(urls)
    }

    @TypeConverter
    fun toCacheComicsUrlsList(urlsString: String): List<CacheComicsUrls>? {
        val type = object : TypeToken<List<CacheComicsUrls>>() {}.type
        return gson.fromJson(urlsString, type)
    }
}
class CacheComicsThumbnailConverter {

    @TypeConverter
    fun fromCacheComicsThumbnail(thumbnail: CacheComicsThumbnail?): String {
        return Gson().toJson(thumbnail)
    }

    @TypeConverter
    fun toCacheComicsThumbnail(thumbnailString: String): CacheComicsThumbnail? {
        val type = object : TypeToken<CacheComicsThumbnail>() {}.type
        return Gson().fromJson(thumbnailString, type)
    }
}

