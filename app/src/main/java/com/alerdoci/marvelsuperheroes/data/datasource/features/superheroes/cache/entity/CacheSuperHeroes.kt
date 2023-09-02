package com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.alerdoci.marvelsuperheroes.data.datasource.features.superheroes.cache.constants.MarvelConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = MarvelConstants.TABLE_NAME)
data class CacheSuperHeroesResult(
    @PrimaryKey(autoGenerate = false)
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

class CacheSuperHeroesThumbnailConverter {

    @TypeConverter
    fun fromCacheSuperHeroesThumbnail(thumbnail: CacheSuperHeroesResult.CacheSuperHeroesThumbnail?): String {
        return Gson().toJson(thumbnail)
    }

    @TypeConverter
    fun toCacheSuperHeroesThumbnail(thumbnailString: String): CacheSuperHeroesResult.CacheSuperHeroesThumbnail? {
        val type = object : TypeToken<CacheSuperHeroesResult.CacheSuperHeroesThumbnail>() {}.type
        return Gson().fromJson(thumbnailString, type)
    }
}

class CacheSuperHeroesComicsConverter {

    @TypeConverter
    fun fromCacheSuperHeroesComics(comics: CacheSuperHeroesResult.CacheSuperHeroesComics?): String {
        return Gson().toJson(comics)
    }

    @TypeConverter
    fun toCacheSuperHeroesComics(comicsString: String): CacheSuperHeroesResult.CacheSuperHeroesComics? {
        val type = object : TypeToken<CacheSuperHeroesResult.CacheSuperHeroesComics>() {}.type
        return Gson().fromJson(comicsString, type)
    }
}

class CacheSuperHeroesSeriesConverter {

    @TypeConverter
    fun fromCacheSuperHeroesSeries(series: CacheSuperHeroesResult.CacheSuperHeroesSeries?): String {
        return Gson().toJson(series)
    }

    @TypeConverter
    fun toCacheSuperHeroesSeries(seriesString: String): CacheSuperHeroesResult.CacheSuperHeroesSeries? {
        val type = object : TypeToken<CacheSuperHeroesResult.CacheSuperHeroesSeries>() {}.type
        return Gson().fromJson(seriesString, type)
    }
}

class CacheSuperHeroesEventsConverter {

    @TypeConverter
    fun fromCacheSuperHeroesEvents(events: CacheSuperHeroesResult.CacheSuperHeroesEvents?): String {
        return Gson().toJson(events)
    }

    @TypeConverter
    fun toCacheSuperHeroesEvents(eventsString: String): CacheSuperHeroesResult.CacheSuperHeroesEvents? {
        val type = object : TypeToken<CacheSuperHeroesResult.CacheSuperHeroesEvents>() {}.type
        return Gson().fromJson(eventsString, type)
    }
}
