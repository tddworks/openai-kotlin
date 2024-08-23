package com.tddworks.ollama.api

import com.tddworks.di.getInstance
import com.tddworks.ollama.di.initOllama
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.koin.test.junit5.AutoCloseKoinTest

class OllamaTestTest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        koinApplication {
            initOllama(
                config = OllamaConfig(
                    baseUrl = { "http://127.0.0.1:8080" },
                )
            )
        }.checkModules()
    }

    @Test
    fun `should return overridden settings`() {
        val target = getInstance<Ollama>()

        assertEquals("http://127.0.0.1:8080", target.baseUrl())
    }

    @Test
    fun `should return default settings`() {
        val target = Ollama.create(ollamaConfig = OllamaConfig())

        assertEquals("http://localhost:11434", target.baseUrl())
    }

}