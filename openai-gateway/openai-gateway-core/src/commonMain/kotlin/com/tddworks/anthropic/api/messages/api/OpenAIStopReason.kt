package com.tddworks.anthropic.api.messages.api

enum class OpenAIStopReason {
    Stop,
    Length,
    FunctionCall,
    ToolCalls,
    Other
}