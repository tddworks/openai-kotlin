package com.tddworks.openllm.api

/**
 * Chat API
 */
interface ChatApi {
    suspend fun chat(request: ChatRequest): ChatResponse
}