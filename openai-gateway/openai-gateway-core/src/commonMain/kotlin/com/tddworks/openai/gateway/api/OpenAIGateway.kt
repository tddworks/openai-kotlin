package com.tddworks.openai.gateway.api

import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.legacy.completions.api.Completions

/**
 * Interface for connecting to the OpenAI Gateway to chat.
 */
interface OpenAIGateway : Chat, Completions