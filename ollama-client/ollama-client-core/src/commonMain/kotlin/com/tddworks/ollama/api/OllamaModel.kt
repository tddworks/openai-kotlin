package com.tddworks.ollama.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class OllamaModel(val value: String) {
    companion object {
        val LLAMA2 = OllamaModel("llama2")
        val LLAMA3 = OllamaModel("llama3")
        val CODE_LLAMA = OllamaModel("codellama")
        val DEEPSEEK_CODER = OllamaModel("deepseek-coder:6.7b-base")
        val MISTRAL = OllamaModel("mistral")
        val availableModels = listOf(LLAMA2, LLAMA3, CODE_LLAMA, MISTRAL, DEEPSEEK_CODER)
    }
}
