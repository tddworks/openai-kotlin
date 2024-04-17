package com.tddworks.ollama.api.chat

import kotlinx.coroutines.flow.Flow

interface OllamaChat {
    fun stream(request: OllamaChatRequest): Flow<OllamaChatResponse>
    suspend fun request(request: OllamaChatRequest): OllamaChatResponse
}