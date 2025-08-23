package com.tddworks.anthropic.api.messages.api

import kotlinx.coroutines.flow.Flow

/** * Anthropic Messages API -https://docs.anthropic.com/claude/reference/messages_post */
interface Messages {
    suspend fun create(request: CreateMessageRequest): CreateMessageResponse

    fun stream(request: CreateMessageRequest): Flow<StreamMessageResponse>
}
