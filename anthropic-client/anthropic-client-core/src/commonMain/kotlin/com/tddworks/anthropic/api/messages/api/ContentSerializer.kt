package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object ContentSerializer : KSerializer<Content> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("Content")

    override fun deserialize(decoder: Decoder): Content {
        val jsonElement = decoder.decodeSerializableValue(JsonElement.serializer())
        return when (jsonElement) {
            is JsonPrimitive -> Content.TextContent(jsonElement.content)
            is JsonArray -> {
                val items = jsonElement.map { element ->
                    val jsonObj = element.jsonObject

                    when (jsonObj["type"]?.jsonPrimitive?.content) {
                        "text" -> BlockMessageContent.TextContent(
                            text = jsonObj["text"]?.jsonPrimitive?.content
                                ?: throw IllegalArgumentException("Missing text")
                        )

                        "image" -> BlockMessageContent.ImageContent(
                            source = BlockMessageContent.ImageContent.Source(
                                mediaType = jsonObj["source"]?.jsonObject?.get("media_type")?.jsonPrimitive?.content
                                    ?: throw IllegalArgumentException("Missing media_type"),
                                data = jsonObj["source"]?.jsonObject?.get("data")?.jsonPrimitive?.content
                                    ?: throw IllegalArgumentException("Missing data"),
                                type = jsonObj["source"]?.jsonObject?.get("type")?.jsonPrimitive?.content
                                    ?: throw IllegalArgumentException("Missing type")
                            )
                        )

                        else -> throw IllegalArgumentException("Unsupported content block type")
                    }

                }
                Content.BlockContent(blocks = items)
            }

            else -> throw IllegalArgumentException("Unsupported content format")
        }
    }

    override fun serialize(encoder: Encoder, value: Content) {
        when (value) {
            is Content.TextContent -> encoder.encodeString(value.text)
            is Content.BlockContent -> encoder.encodeSerializableValue(
                ListSerializer(BlockMessageContent.serializer()), value.blocks
            )
        }
    }
}