package com.tddworks.common.network.api

import io.ktor.client.request.*
import io.ktor.util.reflect.*
import kotlinx.serialization.json.*


/**
 * An interface representing a streamable request.
 * @see [OpenAI's GPT-3 API](https://platform.openai.com/docs/api-reference/streaming)
 * The OpenAI API provides the ability to stream responses back to a client in order to allow partial results for certain requests.
 * To achieve this, we follow the Server-sent events standard.
 * stream boolean or null Optional
 * Defaults to false
 * If set, partial message deltas will be sent, like in ChatGPT. Tokens will be sent as data-only server-sent events as they become available, with the stream terminated by a data: [DONE]
 */
interface StreamableRequest {
    /**
     * Converts the request to a stream request as a [JsonElement].
     * If set, partial message deltas will be sent, like in ChatGPT. Tokens will be sent as data-only server-sent events as they become available, with the stream terminated by a data: [DONE]
     * @return The converted stream request as a [JsonElement].
     */
    fun asStreamRequest(jsonLenient: Json): JsonElement {
        return jsonLenient.encodeToJsonElement(this)
            .jsonObject.toMutableMap()
            .apply {
                put(STREAM, JsonPrimitive(true))
                // Remove type field from kotlinx.serialization
                remove("type")
                // Remove class field from kotlinx.serialization
                // e.g {"#class":"com.tddworks.anthropic.api.messages.api.CreateMessageRequest","messages":[{"role":"user","content":"hello"}],"stream":true}
                remove("#class")
            }
            .let { JsonObject(it) }
    }


    companion object {
        /**
         * The constant value for the stream key.
         */
        const val STREAM = "stream"
    }
}
