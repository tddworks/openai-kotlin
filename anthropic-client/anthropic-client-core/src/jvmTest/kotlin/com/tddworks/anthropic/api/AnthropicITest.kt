package com.tddworks.anthropic.api

import app.cash.turbine.test
import com.tddworks.anthropic.api.messages.api.CreateMessageRequest
import com.tddworks.anthropic.api.messages.api.Message
import com.tddworks.anthropic.di.iniAnthropic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.time.Duration.Companion.seconds

@EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".+")
class AnthropicITest : AutoCloseKoinTest() {
    lateinit var anthropic: Anthropic

    @BeforeEach
    fun setUp() {
        anthropic = iniAnthropic(
            config = AnthropicConfig(
                baseUrl = {
                    System.getenv("ANTHROPIC_BASE_URL") ?: "https://api.anthropic.com"
                },
                apiKey = { System.getenv("ANTHROPIC_API_KEY") ?: "CONFIGURE_ME" },
                anthropicVersion = { "2023-06-01" }
            )
        )
    }


    @Test
    fun `should return correct base url`() {
        assertEquals("https://api.anthropic.com", Anthropic.BASE_URL)
    }


    @Test
    fun `should return stream response`() = runTest {
        anthropic.stream(
            CreateMessageRequest(
                messages = listOf(Message.user("hello")),
                maxTokens = 1024,
                model = AnthropicModel.CLAUDE_3_HAIKU
            )
        ).test(timeout = 10.seconds) {
            assertNotNull(awaitItem())
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return create response`() = runTest {
        //Client request(POST https://api.anthropic.com/v1/messages) invalid: 401 Unauthorized. Text: "{"type":"error","error":{"type":"authentication_error","message":"invalid x-api-key"}}"
        //Client request(POST https://api.anthropic.com/v1/messages) invalid: 400 Bad Request. Text: "{"type":"error","error":{"type":"invalid_request_error","message":"anthropic-version: header is required"}}"
        val r = anthropic.create(
            CreateMessageRequest(
                messages = listOf(Message.user("hello")),
                maxTokens = 1024,
                model = AnthropicModel.CLAUDE_3_HAIKU
            )
        )

        assertNotNull(r.content[0].text)
    }
}