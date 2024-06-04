package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * {
 *  "stop_reason": "end_turn",
 *  "stop_sequence": null,
 *  "usage": {
 *     "output_tokens": 15
 *  }
 * }
 * or
 * {
 * 	"type": "text_delta",
 * 	"text": "!"
 * }
 */
@Serializable
data class Delta(
    val type: String? = null,
    val text: String? = null,
    @SerialName("stop_reason")
    val stopReason: String? = null,
    @SerialName("stop_sequence")
    val stopSequence: String? = null,
    val usage: Usage? = null,
) {
    companion object {
        fun dummy() = Delta(
            type = "text_delta",
            text = "Hello",
            stopReason = "end_turn",
            stopSequence = null,
            usage = Usage(outputTokens = 15)
        )
    }
}