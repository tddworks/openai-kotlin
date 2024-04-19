package com.tddworks.ollama.api

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class OllamaModel(val value: String) {
    companion object {
        val LLAMA2 = OllamaModel("llama2")
        val LLAMA3 = OllamaModel("llama3")
        val CODE_LLAMA = OllamaModel("codellama")
        val MISTRAL = OllamaModel("mistral")
        val availableModels = listOf(LLAMA2, LLAMA3, CODE_LLAMA, MISTRAL)
    }
}