package com.tddworks.anthropic.api.messages.api

import org.koin.core.component.KoinComponent

data class AnthropicConfig(
    val apiKey: () -> String = { "CONFIG_API_KEY" },
    val anthropicVersion: () -> String = { "2023-06-01" },
) : KoinComponent