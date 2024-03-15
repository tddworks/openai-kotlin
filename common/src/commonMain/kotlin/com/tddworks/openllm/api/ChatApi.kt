package com.tddworks.openllm.api

import com.tddworks.common.network.api.StreamChatResponse
import com.tddworks.common.network.api.StreamableRequest
import kotlinx.coroutines.flow.Flow

/**
 * Chat API
 */
interface ChatApi {
    fun chat(request: StreamableRequest): Flow<StreamChatResponse>

    suspend fun chat(request: ChatRequest): ChatResponse
}