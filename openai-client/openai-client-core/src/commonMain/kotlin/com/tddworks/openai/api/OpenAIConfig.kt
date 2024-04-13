package com.tddworks.openai.api

import org.koin.core.component.KoinComponent

data class OpenAIConfig(
    val apiKey: () -> String = { "CONFIG_API_KEY" },
    val baseUrl: () -> String = { OpenAI.BASE_URL },
) : KoinComponent