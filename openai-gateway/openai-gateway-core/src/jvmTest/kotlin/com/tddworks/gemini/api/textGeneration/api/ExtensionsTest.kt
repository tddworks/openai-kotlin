package com.tddworks.gemini.api.textGeneration.api

import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.OpenAIModel
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

@OptIn(ExperimentalSerializationApi::class)
class ExtensionsTest {

    @Test
    fun `should convert gemini GenerateContentResponse to OpenAIChatCompletionChunk`() {
        val generateContentResponse = GenerateContentResponse(
            candidates = listOf(
                Candidate(
                    content = Content(
                        parts = listOf(Part("some-text")),
                        role = "model"
                    )
                )
            ),
            usageMetadata = UsageMetadata(
                promptTokenCount = 4,
                candidatesTokenCount = 724,
                totalTokenCount = 728
            ),
            modelVersion = "gemini-1.5-flash"
        )

        val openAIChatCompletionChunk = generateContentResponse.toOpenAIChatCompletionChunk()

        assertEquals("gemini-1.5-flash", openAIChatCompletionChunk.model)
        assertEquals(1, openAIChatCompletionChunk.choices.size)
        assertEquals("some-text", openAIChatCompletionChunk.choices[0].delta.content)
        assertEquals(0, openAIChatCompletionChunk.choices[0].index)
        assertNull(openAIChatCompletionChunk.choices[0].finishReason)

    }

    @Test
    fun `should convert gemini GenerateContentResponse to OpenAIChatCompletion without finishReason`() {
        val generateContentResponse = GenerateContentResponse(
            candidates = listOf(
                Candidate(
                    content = Content(
                        parts = listOf(Part("some-text")),
                        role = "model"
                    )
                )
            ),
            usageMetadata = UsageMetadata(
                promptTokenCount = 4,
                candidatesTokenCount = 724,
                totalTokenCount = 728
            ),
            modelVersion = "gemini-1.5-flash"
        )

        val openAIChatCompletion = generateContentResponse.toOpenAIChatCompletion()

        assertEquals("gemini-1.5-flash", openAIChatCompletion.model)
        assertEquals(1, openAIChatCompletion.choices.size)
        assertEquals("some-text", openAIChatCompletion.choices[0].message.content)
        assertEquals(0, openAIChatCompletion.choices[0].index)
        assertNull(openAIChatCompletion.choices[0].finishReason)
    }

    @Test
    fun `should convert gemini GenerateContentResponse to OpenAIChatCompletion with all fields`() {
        val generateContentResponse = GenerateContentResponse(
            candidates = listOf(
                Candidate(
                    content = Content(
                        parts = listOf(Part("some-text")),
                        role = "model"
                    ),
                    finishReason = "STOP"
                )
            ),
            usageMetadata = UsageMetadata(
                promptTokenCount = 4,
                candidatesTokenCount = 724,
                totalTokenCount = 728
            ),
            modelVersion = "gemini-1.5-flash"
        )

        val openAIChatCompletion = generateContentResponse.toOpenAIChatCompletion()

        assertEquals("gemini-1.5-flash", openAIChatCompletion.model)
        assertEquals(1, openAIChatCompletion.choices.size)
        assertEquals("some-text", openAIChatCompletion.choices[0].message.content)
        assertEquals(0, openAIChatCompletion.choices[0].index)
        assertEquals("STOP", openAIChatCompletion.choices[0].finishReason?.value)
    }

    @Test
    fun `should convert ChatCompletionRequest to GeminiGenerateContentRequest`() {
        // Given
        val chatCompletionRequest = ChatCompletionRequest(
            model = OpenAIModel(GeminiModel.GEMINI_1_5_FLASH.value),
            messages = listOf(
                ChatMessage.SystemMessage("How are you?"),
                ChatMessage.UserMessage("Hello"),
                ChatMessage.AssistantMessage("Hi there")
            )
        )

        // When
        val generateContentRequest =
            chatCompletionRequest.toGeminiGenerateContentRequest()

        // Then
        assertEquals(GeminiModel.GEMINI_1_5_FLASH, generateContentRequest.model)
        assertEquals(
            "How are you?",
            generateContentRequest.systemInstruction?.parts?.get(0)?.text
        )
        assertNull(generateContentRequest.systemInstruction?.role)

        assertEquals(2, generateContentRequest.contents.size)
        assertEquals("user", generateContentRequest.contents[0].role)
        assertEquals("Hello", generateContentRequest.contents[0].parts[0].text)
        assertEquals("model", generateContentRequest.contents[1].role)
        assertEquals("Hi there", generateContentRequest.contents[1].parts[0].text)
    }
}