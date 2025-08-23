package com.tddworks.ollama.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OllamaConfigTest {

    @Test
    fun `should return overridden settings`() {
        val target = OllamaConfig(baseUrl = { "some-url" }, port = { 8080 }, protocol = { "https" })

        assertEquals("some-url", target.baseUrl())

        assertEquals(8080, target.port())

        assertEquals("https", target.protocol())
    }

    @Test
    fun `should return default settings`() {
        val target = OllamaConfig()

        assertEquals("localhost", target.baseUrl())

        assertEquals(11434, target.port())

        assertEquals("http", target.protocol())
    }
}
