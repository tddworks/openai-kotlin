package com.tddworks.ollama.api.chat

import com.tddworks.common.network.api.StreamableRequest
import com.tddworks.common.network.api.StreamableRequest.Companion.STREAM
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*


@Serializable
data class OllamaChatRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<OllamaChatMessage>,
    @SerialName("format") val format: String? = null,
//    @SerialName("options") val options: Map<String, Any>? = null,
    @SerialName("keep_alive") val keepAlive: String? = null,
) : StreamableRequest {
    fun asNonStreaming(jsonLenient: Json): JsonElement {
        return jsonLenient.encodeToJsonElement(this)
            .jsonObject.toMutableMap()
            .apply {
                put(STREAM, JsonPrimitive(false))
            }
            .let { JsonObject(it) }
    }
}


@Serializable
data class OllamaChatMessage(
    /**
     * `role`: the role of the message, either `system`, `user` or `assistant`
     */
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
    @SerialName("images") val images: List<String>? = null,
)