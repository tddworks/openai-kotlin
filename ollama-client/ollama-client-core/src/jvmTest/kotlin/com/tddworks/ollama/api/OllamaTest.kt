package com.tddworks.ollama.api

import com.tddworks.di.getInstance
import com.tddworks.ollama.di.iniOllama
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

class OllamaTestTest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        iniOllama(
            config = OllamaConfig(
                baseUrl = { "127.0.0.1" },
                port = { 8080 },
                protocol = { "https" }
            )
        )
    }

    @Test
    fun `should return overridden settings`() {
        val target = getInstance<Ollama>()

        assertEquals("127.0.0.1", target.baseUrl())

        assertEquals(8080, target.port())

        assertEquals("https", target.protocol())
    }

    @Test
    fun `should return default settings`() {
        val target = Ollama()

        assertEquals("localhost", target.baseUrl())

        assertEquals(11434, target.port())

        assertEquals("http", target.protocol())
    }

}