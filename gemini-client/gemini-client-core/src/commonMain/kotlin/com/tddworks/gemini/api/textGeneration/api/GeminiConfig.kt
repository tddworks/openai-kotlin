package com.tddworks.gemini.api.textGeneration.api

data class GeminiConfig(
    val apiKey: () -> String = { "CONFIG_API_KEY" },
    val baseUrl: () -> String = { Gemini.BASE_URL },
)
