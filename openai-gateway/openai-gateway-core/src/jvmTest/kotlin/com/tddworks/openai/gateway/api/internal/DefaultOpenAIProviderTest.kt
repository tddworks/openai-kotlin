package com.tddworks.openai.gateway.api.internal

import app.cash.turbine.test
import com.tddworks.common.network.api.ktor.api.ListResponse
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.images.api.Image
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIProviderConfig
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class DefaultOpenAIProviderTest {
    @Mock
    lateinit var client: OpenAI


    private lateinit var provider: OpenAIProvider

    @BeforeEach
    fun setUp() {
        provider =
            OpenAIProvider.openAI(
                config = OpenAIProviderConfig.default(
                    apiKey = { "" },
                ),
                models = listOf(OpenAIModel.GPT_3_5_TURBO),
                openAI = client
            )
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun `should return images from OpenAI API`() = runTest {
        // given
        val request = ImageCreate.create("A cute baby sea otter", OpenAIModel.DALL_E_3)
        val response = ListResponse<Image>(created = 1707567219, data = emptyList())
        whenever(client.generate(request)).thenReturn(response)

        // when
        val images = provider.generate(request)

        // then
        assertEquals(response, images)
    }

    @Test
    fun `should return completions from OpenAI API`() = runTest {
        // given
        val request = CompletionRequest(
            prompt = "Once upon a time",
            suffix = "The end",
            maxTokens = 10,
            temperature = 0.5
        )
        val response = Completion.dummy()
        whenever(client.completions(request)).thenReturn(response)

        // when
        val completions = provider.completions(request)

        // then
        assertEquals(response, completions)
    }

    @Test
    fun `should return chat completions from OpenAI API`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(OllamaModel.LLAMA2.value))
        val response = ChatCompletion.dummy()
        whenever(client.chatCompletions(request)).thenReturn(response)

        // when
        val completions = provider.chatCompletions(request)

        // then
        assertEquals(response, completions)
    }

    @Test
    fun `should stream chat completions for chat`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(OllamaModel.LLAMA2.value))

        val response = ChatCompletionChunk.dummy()
        whenever(client.streamChatCompletions(request)).thenReturn(flow {
            emit(
                response
            )
        })

        // when
        provider.streamChatCompletions(request).test {
            // then
            assertEquals(
                response,
                awaitItem()
            )
            awaitComplete()
        }

    }

    @Test
    fun `should return false when model is not supported`() {
        // Given
        val openAI = mock<OpenAI>()
        val model = OpenAIModel("gpt-3.5-turbo")
        val provider = DefaultOpenAIProvider(
            config = OpenAIProviderConfig.default(
                apiKey = { "" },
            ),
            models = emptyList(),
            openAI = openAI
        )

        // When
        val result = provider.supports(model)

        // Then
        assertTrue(!result)
    }

    @Test
    fun `should return true when model is supported`() {
        // Given
        val openAI = mock<OpenAI>()
        val model = OpenAIModel("gpt-3.5-turbo")
        val provider = DefaultOpenAIProvider(
            config = OpenAIProviderConfig.default(
                apiKey = { "" },
            ),
            models = listOf(model), openAI = openAI
        )

        // When
        val result = provider.supports(model)

        // Then
        assertTrue(result)
    }

}