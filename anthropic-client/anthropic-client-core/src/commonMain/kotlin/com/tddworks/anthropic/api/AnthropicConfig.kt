package com.tddworks.anthropic.api

import org.koin.core.component.KoinComponent

data class AnthropicConfig(
    val apiKey: () -> String = { "CONFIG_API_KEY" },
    val baseUrl: () -> String = { Anthropic.BASE_URL },
    val anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION },
) : KoinComponent


