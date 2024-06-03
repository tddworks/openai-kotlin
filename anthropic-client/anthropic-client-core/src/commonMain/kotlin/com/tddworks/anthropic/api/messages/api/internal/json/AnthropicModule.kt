package com.tddworks.anthropic.api.messages.api.internal.json

import com.tddworks.anthropic.api.messages.api.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object StreamMessageResponseSerializer :
    JsonContentPolymorphicSerializer<StreamMessageResponse>(StreamMessageResponse::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out StreamMessageResponse> {
        val type = element.jsonObject["type"]?.jsonPrimitive?.content

        return when (type) {
            "message_start" -> MessageStart.serializer()
            "content_block_start" -> ContentBlockStart.serializer()
            "content_block_delta" -> ContentBlockDelta.serializer()
            "content_block_stop" -> ContentBlockStop.serializer()
            "message_delta" -> MessageDelta.serializer()
            "message_stop" -> MessageStop.serializer()
            "ping" -> Ping.serializer()
            else -> throw IllegalArgumentException("Unknown type of message")
        }
    }
}