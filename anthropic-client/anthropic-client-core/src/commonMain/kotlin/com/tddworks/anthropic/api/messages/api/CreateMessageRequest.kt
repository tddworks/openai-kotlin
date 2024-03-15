package com.tddworks.anthropic.api.messages.api

import com.tddworks.openllm.api.ChatRequest
import kotlinx.serialization.Serializable

@Serializable
data class CreateMessageRequest(
    val messages: List<Message>,
    val systemPrompt: String? = null,
) : ChatRequest


