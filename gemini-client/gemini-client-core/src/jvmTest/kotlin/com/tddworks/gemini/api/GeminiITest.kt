package com.tddworks.gemini.api

import app.cash.turbine.test
import com.tddworks.di.getInstance
import com.tddworks.gemini.api.textGeneration.api.*
import com.tddworks.gemini.di.initGemini
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.time.Duration.Companion.seconds


@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
class GeminiITest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        initGemini(
            config = GeminiConfig(
                apiKey = { System.getenv("GEMINI_API_KEY") ?: "CONFIGURE_ME" },
                baseUrl = { System.getenv("GEMINI_BASE_URL") ?: Gemini.BASE_URL }
            ))
    }

    @Test
    fun `should return stream generateContent response`() = runTest {
        val gemini = getInstance<Gemini>()

        gemini.streamGenerateContent(
            GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part.TextPart(text = "hello")))),
                stream = true
            )
        ).test(timeout = 10.seconds) {
            assertNotNull(awaitItem())
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return generateContent response`() = runTest {
        val gemini = getInstance<Gemini>()

        val response = gemini.generateContent(
            GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part.TextPart(text = "hello")))),
            )
        )

        assertNotNull(response)
    }
}