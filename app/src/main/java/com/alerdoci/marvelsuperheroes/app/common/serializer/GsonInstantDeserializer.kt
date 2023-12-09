package com.alerdoci.marvelsuperheroes.app.common.serializer

import com.alerdoci.marvelsuperheroes.app.common.constants.supportedDateFormat
import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.format
import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.iif
import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.toInstant
import com.google.gson.*
import java.lang.String.format
import java.lang.reflect.Type
import java.text.ParseException
import java.time.Instant
import java.util.Date

// With this class we can parse some date formats received into json data, and is very simple to extend to new formats
class GsonInstantDeserializer: JsonDeserializer<Instant>, JsonSerializer<Instant> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Instant? {
        for (format in supportedDateFormat) {
            try {
                val stringDate = json?.asJsonPrimitive?.asString

                return stringDate?.toInstant(format)
            } catch (_: Exception) {

            }
        }

        throw ParseException("Error parsing date", 0)
    }

    override fun serialize(
        src: Instant?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        TODO("Not yet implemented")
    }

}
