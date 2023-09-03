package com.alerdoci.marvelsuperheroes.app.common.serializer

import com.alerdoci.marvelsuperheroes.app.common.constants.supportedDateFormat
import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.format
import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.iif
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class GsonDateDeserializer: JsonDeserializer<Date>, JsonSerializer<Date> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {
        for (format in supportedDateFormat){
            try {
                val stringDate = json?.asJsonPrimitive?.asString

                return stringDate?.let { SimpleDateFormat(format).parse(it) } as Date
            } catch (_: Exception) {

            }
        }

        throw ParseException("Error parsing date", 0)
    }

    override fun serialize(
        src: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        return (src == null).iif(null, JsonPrimitive(src.format("yyyy-MM-dd'T'HH:mm:ss")))
    }

}
