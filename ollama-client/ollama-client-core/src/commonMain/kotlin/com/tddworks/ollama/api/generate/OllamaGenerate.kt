package com.tddworks.ollama.api.generate

import kotlinx.coroutines.flow.Flow

/** Interface for Ollama generate */
interface OllamaGenerate {
    fun stream(request: OllamaGenerateRequest): Flow<OllamaGenerateResponse>

    suspend fun request(request: OllamaGenerateRequest): OllamaGenerateResponse
}
