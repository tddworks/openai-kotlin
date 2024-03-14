package com.tddworks.common.network.api

import com.tddworks.common.network.api.ktor.internal.JsonLenient
import io.ktor.client.request.*
import io.ktor.util.reflect.*
import kotlinx.serialization.Serializable
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
@Serializable
sealed interface StreamableRequest {
    /**
     * Converts the request to a stream request as a [JsonElement].
     * If set, partial message deltas will be sent, like in ChatGPT. Tokens will be sent as data-only server-sent events as they become available, with the stream terminated by a data: [DONE]
     * @return The converted stream request as a [JsonElement].
     */
    fun asStreamRequest(): JsonElement {
        return JsonLenient.encodeToJsonElement(this)
            .jsonObject.toMutableMap()
            .apply {
                put(STREAM, JsonPrimitive(true))
                // Remove type field from kotlinx.serialization
                remove("type")
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
