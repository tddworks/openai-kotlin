package com.tddworks.ollama.api.chat.api

import com.tddworks.ollama.api.OllamaModel
import com.tddworks.ollama.api.chat.OllamaChatMessage
import com.tddworks.ollama.api.chat.OllamaChatResponse
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.Model
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalSerializationApi::class)
class ExtensionsTest {

    @Test
    fun `should convert OllamaChatResponse to OpenAIChatCompletion`() {
        val ollamaChatResponse = OllamaChatResponse(
            createdAt = "123",
            model = "llama2",
            message = OllamaChatMessage(
                content = "Hi there",
                role = "assistant",
                images = emptyList()
            ),
            done = true
        )
        val openAIChatCompletion = ollamaChatResponse.toOpenAIChatCompletion()
        assertEquals("123", openAIChatCompletion.id)
        assertEquals(1L, openAIChatCompletion.created)
        assertEquals("llama2", openAIChatCompletion.model)
        assertEquals(1, openAIChatCompletion.choices.size)
        assertEquals("Hi there", openAIChatCompletion.choices[0].message.content)
        assertEquals("assistant", openAIChatCompletion.choices[0].message.role.name)
    }

    @Test
    fun `should convert ChatCompletionRequest to OllamaChatRequest`() {
        val chatCompletionRequest = ChatCompletionRequest(
            model = Model(OllamaModel.LLAMA2.value),
            messages = listOf(
                ChatMessage.UserMessage("Hello"),
                ChatMessage.AssistantMessage("Hi there"),
                ChatMessage.SystemMessage("How are you?")
            )
        )
        val ollamaChatRequest = chatCompletionRequest.toOllamaChatRequest()
        assertEquals("llama2", ollamaChatRequest.model)
        assertEquals(3, ollamaChatRequest.messages.size)
        assertEquals("user", ollamaChatRequest.messages[0].role)
        assertEquals("Hello", ollamaChatRequest.messages[0].content)
        assertEquals("assistant", ollamaChatRequest.messages[1].role)
        assertEquals("Hi there", ollamaChatRequest.messages[1].content)
        assertEquals("system", ollamaChatRequest.messages[2].role)
        assertEquals("How are you?", ollamaChatRequest.messages[2].content)
    }
}
