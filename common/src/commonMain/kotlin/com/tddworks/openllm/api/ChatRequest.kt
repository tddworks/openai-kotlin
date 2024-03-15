package com.tddworks.openllm.api

import com.tddworks.common.network.api.StreamableRequest

/**
 * Chat request
 */

interface ChatRequest

interface StreamableChatRequest: StreamableRequest

enum class ChatProvider {
    OPENAI,
    ANTHROPIC
}