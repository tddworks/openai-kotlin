package com.tddworks.ollama.api.chat.api

import com.tddworks.ollama.api.OllamaModel
import com.tddworks.ollama.api.chat.OllamaChatMessage
import com.tddworks.ollama.api.chat.OllamaChatResponse
import com.tddworks.ollama.api.generate.OllamaGenerateResponse
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.Model
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalSerializationApi::class)
class ExtensionsTest {

    @Test
    fun `should convert CompletionRequest to OllamaGenerateRequest`() {
        val completionRequest = CompletionRequest(
            model = Model(OllamaModel.CODE_LLAMA.value),
            prompt = "Once upon a time",
            suffix = "The end",
            maxTokens = 10,
            temperature = 0.5,
            stream = false,
            streamOptions = mapOf(
                "raw" to true,
                "temperature" to 0.5,
                "stop" to arrayOf("<EOT>"),
                "num_predict" to 100
            )
        )

        val ollamaGenerateRequest = completionRequest.toOllamaGenerateRequest()
        assertEquals("codellama", ollamaGenerateRequest.model)
        assertEquals("Once upon a time", ollamaGenerateRequest.prompt)
        assertFalse(ollamaGenerateRequest.stream)

        assertTrue(ollamaGenerateRequest.raw)

        with(ollamaGenerateRequest.options) {
            assertNotNull(this)
            assertEquals(0.5, this?.get("temperature"))
            assertEquals(100, this?.get("num_predict"))
            assertEquals("<EOT>", (this?.get("stop") as Array<*>)[0])
        }
    }

    @Test
    fun `should convert OllamaGenerateResponse to OpenAICompletion`() {
        val ollamaGenerateResponse = OllamaGenerateResponse.dummy()
        val openAICompletion = ollamaGenerateResponse.toOpenAICompletion()
        assertEquals("createdAt", openAICompletion.id)
        assertEquals(1, openAICompletion.created)
        assertEquals("some-model", openAICompletion.model)
        assertEquals(1, openAICompletion.choices.size)
        assertEquals("response", openAICompletion.choices[0].text)
        assertEquals(0, openAICompletion.choices[0].index)
        assertEquals("doneReason", openAICompletion.choices[0].finishReason)
        assertEquals(10, openAICompletion.usage?.promptTokens)
        assertEquals(10, openAICompletion.usage?.completionTokens)
        assertEquals(20, openAICompletion.usage?.totalTokens)
    }

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
