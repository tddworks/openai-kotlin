package com.tddworks.openai.api.chat.api.vision

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object VisionMessageContentSerializer :
    JsonContentPolymorphicSerializer<VisionMessageContent>(VisionMessageContent::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out VisionMessageContent> {
        val typeObject = element.jsonObject["type"]
        val jsonPrimitive = typeObject?.jsonPrimitive
        val type = jsonPrimitive?.content

        return when (type) {
            ContentType.TEXT.value -> VisionMessageContent.TextContent.serializer()
            ContentType.IMAGE.value -> VisionMessageContent.ImageContent.serializer()
            else -> throw IllegalArgumentException("Unknown type")
        }
    }
}