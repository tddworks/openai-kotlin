package com.tddworks.anthropic.api.messages.api

import com.tddworks.openllm.api.ChatRequest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
@ExperimentalSerializationApi
data class CreateMessageRequest(
    val messages: List<Message>,
    val systemPrompt: String? = null,
) : ChatRequest


