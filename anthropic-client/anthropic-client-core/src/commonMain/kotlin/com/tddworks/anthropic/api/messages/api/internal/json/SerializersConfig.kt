package com.tddworks.anthropic.api.messages.api.internal.json

import com.tddworks.anthropic.api.messages.api.CreateMessageRequest
import com.tddworks.anthropic.api.messages.api.CreateMessageResponse
import com.tddworks.anthropic.api.messages.api.stream.*
import com.tddworks.common.network.api.StreamChatResponse
import com.tddworks.common.network.api.StreamableRequest
import com.tddworks.openllm.api.ChatRequest
import com.tddworks.openllm.api.ChatResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val chatModule = SerializersModule {

//    polymorphicDefaultSerializer(StreamableRequest::class) { instance ->
//        @Suppress("UNCHECKED_CAST")
//        when (instance) {
//            is CreateMessageRequest -> CreateMessageRequest.serializer() as SerializationStrategy<StreamableRequest>
//            else -> null
//        }
//    }

    polymorphic(StreamableRequest::class) {
        subclass(CreateMessageRequest::class, CreateMessageRequest.serializer())
        defaultDeserializer { CreateMessageRequest.serializer() }
    }
//
    polymorphic(ChatRequest::class) {
        subclass(CreateMessageRequest::class, CreateMessageRequest.serializer())
        defaultDeserializer { CreateMessageRequest.serializer() }
    }
//
//    polymorphic(ChatResponse::class) {
//        subclass(CreateMessageResponse::class, CreateMessageResponse.serializer())
//        defaultDeserializer { ChatResponseSerializer }
//    }

    polymorphic(StreamChatResponse::class) {
        defaultDeserializer { StreamChatResponseSerializer }
    }

//    polymorphicDefaultSerializer(ChatResponse::class) { instance ->
//        @Suppress("UNCHECKED_CAST")
//        when (instance) {
//            is CreateMessageResponse -> CreateMessageResponse.serializer() as SerializationStrategy<ChatResponse>
//            else -> null
//        }
//    }
//
    polymorphic(ChatResponse::class) {
        defaultDeserializer { ChatResponseSerializer }
    }
}

object ChatResponseSerializer :
    JsonContentPolymorphicSerializer<ChatResponse>(ChatResponse::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out ChatResponse> {
        val content = element.jsonObject["content"]

        return when {
            content != null -> CreateMessageResponse.serializer()
            else -> throw IllegalArgumentException("Unknown type of message")
        }
    }
}

object StreamChatResponseSerializer :
    JsonContentPolymorphicSerializer<StreamChatResponse>(StreamChatResponse::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out StreamChatResponse> {
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