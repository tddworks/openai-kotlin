package com.tddworks.ollama.api.chat.internal.json

import com.tddworks.common.network.api.StreamableRequest
import com.tddworks.ollama.api.chat.OllamaChatRequest
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * The `SerializersModule` that defines the serialization and deserialization
 * rules for the `StreamableRequest` class and its subclasses.
 */
val ollamaModule = SerializersModule {
    /**
     * Registers a polymorphic serialization/deserialization for the
     * `StreamableRequest` class.
     */
    polymorphic(StreamableRequest::class) {
        /**
         * Registers a subclass serializer for the `OllamaChatRequest` class.
         *
         * @param OllamaChatRequest.serializer() The serializer for the `OllamaChatRequest` class.
         */
        subclass(OllamaChatRequest::class, OllamaChatRequest.serializer())
        /**
         * Registers a default deserializer for the `StreamableRequest` class.
         *
         * @param { OllamaChatRequest.serializer() } The deserializer for the `StreamableRequest` class.
         */
        defaultDeserializer { OllamaChatRequest.serializer() }
    }
}

