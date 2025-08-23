package com.tddworks.ollama.api.chat

import com.tddworks.common.network.api.ktor.api.AnySerial
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** https://github.com/ollama/ollama/blob/main/docs/api.md Generate a chat completion */
@Serializable
data class OllamaChatRequest(
    /** (required) the model name */
    @SerialName("model") val model: String,
    /** (required) a list of messages to send to the model */
    @SerialName("messages") val messages: List<OllamaChatMessage>,
    /**
     * Advanced parameters (optional): the format to return a response in. Currently the only
     * accepted value is json
     */
    @SerialName("format") val format: String? = null,
    /**
     * additional model parameters listed in the documentation for the Modelfile such as temperature
     */
    @SerialName("options") val options: Map<String, AnySerial>? = null,
    /**
     * keep_alive: controls how long the model will stay loaded into memory following the request
     * (default: 5m)
     */
    @SerialName("keep_alive") val keepAlive: String? = null,
    /**
     * stream: if false the response will be returned as a single response object, rather than a
     * stream of objects
     */
    @SerialName("stream") val stream: Boolean? = null,
) {

    companion object {
        fun dummy() =
            OllamaChatRequest(
                model = "llama2",
                messages = listOf(OllamaChatMessage(role = "user", content = "Hello!")),
            )
    }
}

@Serializable
data class OllamaChatMessage(
    /** `role`: the role of the message, either `system`, `user` or `assistant` */
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
    @SerialName("images") val images: List<String>? = null,
)
