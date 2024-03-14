package com.tddworks.anthropic.api.messages.api.internal.json

import com.tddworks.anthropic.api.messages.api.CreateMessageRequest
import com.tddworks.anthropic.api.messages.api.CreateMessageResponse
import com.tddworks.openllm.api.ChatRequest
import com.tddworks.openllm.api.ChatResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@OptIn(ExperimentalSerializationApi::class)
val chatModule = SerializersModule {
    polymorphic(ChatRequest::class) {
        subclass(CreateMessageRequest::class, CreateMessageRequest.serializer())
        defaultDeserializer { CreateMessageRequest.serializer() }
    }
    polymorphic(ChatResponse::class) {
        subclass(CreateMessageResponse::class, CreateMessageResponse.serializer())
        defaultDeserializer { ChatMessageSerializer }
    }

//    polymorphicDefaultSerializer(ChatResponse::class) { instance ->
//        @Suppress("UNCHECKED_CAST")
//        when (instance) {
//            is CreateMessageResponse -> CreateMessageResponse.serializer() as SerializationStrategy<ChatResponse>
//            else -> null
//        }
//    }
}

object ChatMessageSerializer :
    JsonContentPolymorphicSerializer<ChatResponse>(ChatResponse::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out ChatResponse> {
        val content = element.jsonObject["content"]

        return when {
            content != null -> CreateMessageResponse.serializer()
            else -> throw IllegalArgumentException("Unknown type of message")
        }
    }
}