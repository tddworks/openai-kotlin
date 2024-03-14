package com.tddworks.openllm.api

/**
 * Chat request
 */

interface ChatRequest

enum class ChatProvider {
    OPENAI,
    ANTHROPIC
}