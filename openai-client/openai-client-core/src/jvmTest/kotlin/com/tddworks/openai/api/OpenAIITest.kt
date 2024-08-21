package com.tddworks.openai.api

import app.cash.turbine.test
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.di.initOpenAI
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.time.Duration.Companion.seconds

@ExperimentalSerializationApi
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
class OpenAIITest : AutoCloseKoinTest() {
    private lateinit var openAI: OpenAI

    @BeforeEach
    fun setUp() {
        openAI = initOpenAI(OpenAIConfig(
            baseUrl = { OpenAI.BASE_URL },
            apiKey = { System.getenv("OPENAI_API_KEY") ?: "CONFIGURE_ME" }
        ))
    }


    @Test
    fun `should use openai client to get stream chat completions`() = runTest {
        openAI.streamChatCompletions(
            ChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                openAIModel = OpenAIModel.GPT_3_5_TURBO
            )
        ).test(timeout = 10.seconds) {
            assertNotNull(awaitItem())
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should use openai client to get chat completions`() = runTest {
        val response = openAI.chatCompletions(
            ChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                openAIModel = OpenAIModel.GPT_3_5_TURBO
            )
        )
        assertNotNull(response)
    }
}