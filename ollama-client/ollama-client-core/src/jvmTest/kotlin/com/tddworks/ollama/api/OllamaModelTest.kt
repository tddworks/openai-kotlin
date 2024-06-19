package com.tddworks.ollama.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OllamaModelTest {

    @Test
    fun `should return correct latest API model name`() {
        assertEquals("deepseek-coder:6.7b", OllamaModel.DEEPSEEK_CODER.value)
        assertEquals("llama3", OllamaModel.LLAMA3.value)
        assertEquals("llama2", OllamaModel.LLAMA2.value)
        assertEquals("codellama", OllamaModel.CODE_LLAMA.value)
        assertEquals("mistral", OllamaModel.MISTRAL.value)
    }
}