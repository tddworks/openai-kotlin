package com.tddworks.openai.gateway.api.internal

import app.cash.turbine.test
import com.tddworks.gemini.api.textGeneration.api.*
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.OpenAIProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse

@OptIn(ExperimentalSerializationApi::class)
class GeminiOpenAIProviderTest {
    private lateinit var client: Gemini
    private lateinit var config: GeminiOpenAIProviderConfig

    private lateinit var provider: OpenAIProvider

    @BeforeEach
    fun setUp() {
        client = mock()
        config = mock()
        provider = OpenAIProvider.gemini(
            client = client,
            config = config
        )
    }

    @Test
    fun `should throw not supported when invoke generate`() = runTest {
        // given
        val request = ImageCreate.create("A cute baby sea otter", OpenAIModel.DALL_E_3)

        runCatching {
            // when
            provider.generate(request)
        }.onFailure {
            // then
            assertEquals("Not supported", it.message)
        }
    }

    @Test
    fun `should throw not supported when invoke completions`() = runTest {
        // given
        val request = CompletionRequest(
            prompt = "Once upon a time",
            suffix = "The end",
            maxTokens = 10,
            temperature = 0.5
        )

        runCatching {
            // when
            provider.completions(request)
        }.onFailure {
            // then
            assertEquals("Not supported", it.message)
        }
    }

    @Test
    fun `should fetch chat completions from OpenAI API`() = runTest {
        // given
        val request =
            ChatCompletionRequest.dummy(OpenAIModel(GeminiModel.GEMINI_1_5_FLASH.value))
        val response = GenerateContentResponse.dummy()
        whenever(client.generateContent(request.toGeminiGenerateContentRequest())).thenReturn(
            response
        )

        // when
        val completions = provider.chatCompletions(request)

        // then
        assertEquals(response.toOpenAIChatCompletion(), completions)
    }

    @Test
    fun `should stream chat completions for chat`() = runTest {
        // given
        val request =
            ChatCompletionRequest.dummy(OpenAIModel(GeminiModel.GEMINI_1_5_FLASH.value))

        val response = GenerateContentResponse.dummy()
        whenever(
            client.streamGenerateContent(
                request.toGeminiGenerateContentRequest().copy(stream = true)
            )
        ).thenReturn(
            flow {
                emit(
                    response
                )
            })

        // when
        provider.streamChatCompletions(request).test {
            // then
            assertEquals(
                response.toOpenAIChatCompletionChunk(),
                awaitItem()
            )
            awaitComplete()
        }

    }

    @Test
    fun `should return false when model is not supported`() {
        // given
        val model = OpenAIModel(OpenAIModel.GPT_3_5_TURBO.value)

        // when
        val supported = provider.supports(model)

        // then
        assertFalse(supported)
    }

    @Test
    fun `should return true when model is supported`() {
        // given
        val model = OpenAIModel(GeminiModel.GEMINI_1_5_FLASH.value)

        // when
        val supported = provider.supports(model)

        // then
        assertTrue(supported)
    }
}