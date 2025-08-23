package com.tddworks.ollama.api.internal

import com.tddworks.di.getInstance
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.generate.OllamaGenerate

class OllamaApi(
    private val config: OllamaConfig,
    private val ollamaChat: OllamaChat,
    private val ollamaGenerate: OllamaGenerate,
) : Ollama, OllamaChat by ollamaChat, OllamaGenerate by ollamaGenerate {

    override fun baseUrl(): String {
        return config.baseUrl()
    }

    override fun port(): Int {
        return config.port()
    }

    override fun protocol(): String {
        return config.protocol()
    }
}

fun Ollama.Companion.create(
    config: OllamaConfig,
    ollamaChat: OllamaChat = getInstance(),
    ollamaGenerate: OllamaGenerate = getInstance(),
): Ollama {
    return OllamaApi(config = config, ollamaChat = ollamaChat, ollamaGenerate = ollamaGenerate)
}
