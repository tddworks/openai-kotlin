package com.tddworks.ollama.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class OllamaConfigTest {

    @Test
    fun `should return overridden settings`() {
        val target = OllamaConfig(
            baseUrl = { "some-url" }
        )

        assertEquals("some-url", target.baseUrl())
    }

    @Test
    fun `should return default settings`() {
        val target = OllamaConfig()

        assertEquals("http://localhost:11434", target.baseUrl())
    }
}