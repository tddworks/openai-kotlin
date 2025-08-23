package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * { "content": [ { "text": "Hi! My name is Claude.", "type": "text" } ], "id":
 * "msg_013Zva2CMHLNnXjNJJKqJ2EF", "model": "claude-3-opus-20240229", "role": "assistant",
 * "stop_reason": "end_turn", "stop_sequence": null, "type": "message", "usage": { "input_tokens":
 * 10, "output_tokens": 25 } }
 */
@Serializable
data class CreateMessageResponse(
    val content: List<ContentMessage>,
    val id: String,
    val model: String,
    val role: String,
    @SerialName("stop_reason") val stopReason: String?,
    @SerialName("stop_sequence") val stopSequence: String?,
    val type: String,
    val usage: Usage,
) {
    companion object {
        fun dummy() =
            CreateMessageResponse(
                content = listOf(ContentMessage(text = "Hi! My name is Claude.", type = "text")),
                id = "msg_1nZdL29xx5MUA1yADyHTEsnR8uuvGzszyY",
                model = "claude-3-opus-20240229",
                role = "assistant",
                stopReason = null,
                stopSequence = null,
                type = "message",
                usage = Usage(inputTokens = 25, outputTokens = 1),
            )
    }
}
