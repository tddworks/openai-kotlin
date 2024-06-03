package com.tddworks.ollama.api.chat

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
    @SerialName("stream")
    val stream: Boolean? = null,
) {

    companion object {
        fun dummy() = OllamaChatRequest(
            model = "llama2",
            messages = listOf(
                OllamaChatMessage(
                    role = "user",
                    content = "Hello!"
                )
            )
        )
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