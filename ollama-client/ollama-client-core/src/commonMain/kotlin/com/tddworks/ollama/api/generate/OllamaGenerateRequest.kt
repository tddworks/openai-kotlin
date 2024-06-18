package com.tddworks.ollama.api.generate

import com.tddworks.ollama.api.chat.AnySerial
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OllamaGenerateRequest(
    @SerialName("model") val model: String,
    @SerialName("prompt") val prompt: String,
    @SerialName("stream") val stream: Boolean = false,
    @SerialName("raw") val raw: Boolean = false,
    @SerialName("options") val options: Map<String, AnySerial>? = null,
)