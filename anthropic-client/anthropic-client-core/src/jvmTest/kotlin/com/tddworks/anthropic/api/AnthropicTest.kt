package com.tddworks.anthropic.api

import com.tddworks.anthropic.di.anthropicModules
import com.tddworks.anthropic.di.initKoin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

class AnthropicTest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        initKoin(
            module = anthropicModules({ "http://localhost:8080" }, { "1234" }, { "2024-03-01" })
        )
    }

    @Test
    fun `should return overridden settings`() {
        val target = Anthropic(
            baseUrl = { "http://localhost:8080" },
            apiKey = { "1234" },
            anthropicVersion = { "2024-03-01" }
        )

        assertEquals("http://localhost:8080", target.baseUrl())

        assertEquals("1234", target.apiKey())

        assertEquals("2024-03-01", target.anthropicVersion())
    }

    @Test
    fun `should return default settings`() {
        val target = Anthropic()

        assertEquals("api.anthropic.com", target.baseUrl())

        assertEquals("CONFIGURE_ME", target.apiKey())

        assertEquals("CONFIGURE_ME", target.apiKey())

        assertEquals("2023-06-01", target.anthropicVersion())
    }

}