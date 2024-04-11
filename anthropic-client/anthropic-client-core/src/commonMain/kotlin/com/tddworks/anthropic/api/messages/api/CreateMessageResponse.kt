package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * {
 *   "content": [
 *     {
 *       "text": "Hi! My name is Claude.",
 *       "type": "text"
 *     }
 *   ],
 *   "id": "msg_013Zva2CMHLNnXjNJJKqJ2EF",
 *   "model": "claude-3-opus-20240229",
 *   "role": "assistant",
 *   "stop_reason": "end_turn",
 *   "stop_sequence": null,
 *   "type": "message",
 *   "usage": {
 *     "input_tokens": 10,
 *     "output_tokens": 25
 *   }
 * }
 */
@Serializable
data class CreateMessageResponse(
    val content: List<ContentMessage>,
    val id: String,
    val model: String,
    val role: String,
    @SerialName("stop_reason")
    val stopReason: String?,
    @SerialName("stop_sequence")
    val stopSequence: String?,
    val type: String,
    val usage: Usage,
)


@Serializable
data class ContentMessage(val text: String, val type: String)

@Serializable
data class Usage(
    @SerialName("input_tokens")
    val inputTokens: Int? = null,
    @SerialName("output_tokens")
    val outputTokens: Int? = null,
)


