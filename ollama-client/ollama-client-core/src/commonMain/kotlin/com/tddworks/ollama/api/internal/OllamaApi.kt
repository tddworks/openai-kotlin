package com.tddworks.ollama.api.internal

import com.tddworks.di.getInstance
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.generate.OllamaGenerate

class OllamaApi(
    private val baseUrl: String,
    private val port: Int,
    private val protocol: String,
) : Ollama, OllamaChat by getInstance(), OllamaGenerate by getInstance() {

    override fun baseUrl(): String {
        return baseUrl
    }

    override fun port(): Int {
        return port
    }

    override fun protocol(): String {
        return protocol
    }

}