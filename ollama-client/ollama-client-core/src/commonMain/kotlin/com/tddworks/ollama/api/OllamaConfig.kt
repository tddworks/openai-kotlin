package com.tddworks.ollama.api

import org.koin.core.component.KoinComponent

data class OllamaConfig(
    val apiKey: () -> String = { "CONFIG_API_KEY" },
    val baseUrl: () -> String = { Ollama.BASE_URL },
    val ollamaVersion: () -> String = { Ollama.ANTHROPIC_VERSION },
) : KoinComponent