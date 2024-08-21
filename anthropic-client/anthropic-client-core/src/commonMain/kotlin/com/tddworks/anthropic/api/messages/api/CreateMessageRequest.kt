package com.tddworks.anthropic.api.messages.api

import com.tddworks.anthropic.api.AnthropicModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMessageRequest(
    val messages: List<Message>,
    val systemPrompt: String? = null,
    @SerialName("max_tokens")
    val maxTokens: Int = 1024,
    @SerialName("model")
    val model: AnthropicModel = AnthropicModel.CLAUDE_3_HAIKU,
    val stream: Boolean? = null,
) {
    companion object {
        fun streamRequest(messages: List<Message>, systemPrompt: String? = null) =
            CreateMessageRequest(messages, systemPrompt, stream = true)
    }
}


