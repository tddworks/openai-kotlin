package com.tddworks.ollama.api

import org.koin.core.component.KoinComponent

data class OllamaConfig(
    val baseUrl: () -> String = { Ollama.BASE_URL },
    val protocol: () -> String = { Ollama.PROTOCOL },
    val port: () -> Int = { Ollama.PORT },
) : KoinComponent