package com.tddworks.ollama.api.chat

import com.tddworks.common.network.api.StreamableRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OllamaChatRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<OllamaChatMessage>,
    @SerialName("format") val format: String? = null,
//    @SerialName("options") val options: Map<String, Any>? = null,
//    @SerialName("stream") val stream: Boolean? = null,
    @SerialName("keep_alive") val keepAlive: String? = null,
) : StreamableRequest


@Serializable
data class OllamaChatMessage(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
    @SerialName("images") val images: List<String>? = null,
)