package com.tddworks.anthropic.api.messages.api

import com.tddworks.anthropic.api.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMessageRequest(
    val messages: List<Message>,
    val systemPrompt: String? = null,
    @SerialName("max_tokens")
    val maxTokens: Int = 1024,
    @SerialName("model")
    val model: Model = Model.CLAUDE_3_HAIKU,
) : StreamMessageRequest {
    companion object {
        fun streamRequest(messages: List<Message>, systemPrompt: String? = null) =
            CreateMessageRequest(messages, systemPrompt) as StreamMessageRequest
    }
}


