package com.tddworks.gemini.api.textGeneration.api

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

/**
 * { "contents":
 * [{ "parts":[ {"text": "Tell me about this instrument"}, { "inline_data": { "mime_type":"image/jpeg", "data": "$(cat "$TEMP_B64")" } } ]
 * }] }
 */
object PartSerializer : JsonContentPolymorphicSerializer<Part>(Part::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out Part> {
        val jsonObject = element.jsonObject
        return when {
            "text" in jsonObject -> Part.TextPart.serializer()
            "inline_data" in jsonObject -> Part.InlineDataPart.serializer()
            else -> throw SerializationException("Unknown Part type")
        }
    }
}
