package com.tddworks.anthropic.api.messages.api

import com.tddworks.common.network.api.StreamableRequest
import com.tddworks.openllm.api.ChatRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("CreateMessageRequest")
data class CreateMessageRequest(
    val messages: List<Message>,
    val systemPrompt: String? = null,
) : ChatRequest, StreamableRequest {
    companion object {
        fun streamRequest(messages: List<Message>, systemPrompt: String? = null) =
            CreateMessageRequest(messages, systemPrompt) as StreamableRequest
    }
}


