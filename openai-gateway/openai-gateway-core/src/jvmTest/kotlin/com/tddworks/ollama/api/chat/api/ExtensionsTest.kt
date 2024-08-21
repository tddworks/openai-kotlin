package com.tddworks.ollama.api.chat.api

import com.tddworks.common.network.api.ktor.api.AnySerial
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.ollama.api.chat.OllamaChatMessage
import com.tddworks.ollama.api.chat.OllamaChatResponse
import com.tddworks.ollama.api.generate.OllamaGenerateResponse
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.chat.api.Role
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.api.legacy.completions.api.Usage
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalSerializationApi::class)
class ExtensionsTest {

    @Test
    fun `should convert CompletionRequest to OllamaGenerateRequest with required fields`() {
        val completionRequest = CompletionRequest(
            model = OpenAIModel(OllamaModel.CODE_LLAMA.value),
            prompt = "Once upon a time",
        )

        val ollamaGenerateRequest = completionRequest.toOllamaGenerateRequest()
        assertEquals("codellama", ollamaGenerateRequest.model)
        assertEquals("Once upon a time", ollamaGenerateRequest.prompt)
        assertFalse(ollamaGenerateRequest.stream)

        assertFalse(ollamaGenerateRequest.raw)

        assertEquals(emptyMap<String, AnySerial>(), ollamaGenerateRequest.options)
    }

    @Test
    fun `should convert CompletionRequest to OllamaGenerateRequest with all fields`() {
        val completionRequest = CompletionRequest(
            model = OpenAIModel(OllamaModel.CODE_LLAMA.value),
            prompt = "Once upon a time",
            suffix = "The end",
            maxTokens = 10,
            temperature = 0.5,
            stream = false,
            stop = "<EOT>,<EOL>",
            streamOptions = mapOf(
                "raw" to true
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
            assertEquals(10, this?.get("num_predict"))
            val stops = this?.get("stop") as Array<*>
            assertEquals(2, stops.size)
            assertEquals("<EOT>", stops[0])
        }
    }

    @Test
    fun `should convert OllamaGenerateResponse to OpenAICompletion with required fields`() {
        val ollamaGenerateResponse = OllamaGenerateResponse(
            model = "some-model",
            createdAt = "createdAt",
            response = "response",
            done = false,
        )
        val openAICompletion = ollamaGenerateResponse.toOpenAICompletion()
        assertEquals("createdAt", openAICompletion.id)
        assertEquals(1, openAICompletion.created)
        assertEquals("some-model", openAICompletion.model)
        assertEquals(1, openAICompletion.choices.size)
        assertEquals("response", openAICompletion.choices[0].text)
        assertEquals(0, openAICompletion.choices[0].index)
        assertEquals("", openAICompletion.choices[0].finishReason)
        assertEquals(Usage(totalTokens = 0), openAICompletion.usage)
    }

    @Test
    fun `should convert OllamaGenerateResponse to OpenAICompletion without promptEvalCount`() {
        val ollamaGenerateResponse = OllamaGenerateResponse(
            model = "some-model",
            createdAt = "createdAt",
            response = "response",
            done = false,
            evalCount = 10,
            evalDuration = 1000,
            loadDuration = 1000,
            promptEvalDuration = 1000,
        )
        val openAICompletion = ollamaGenerateResponse.toOpenAICompletion()
        assertEquals("createdAt", openAICompletion.id)
        assertEquals(1, openAICompletion.created)
        assertEquals("some-model", openAICompletion.model)
        assertEquals(1, openAICompletion.choices.size)
        assertEquals("response", openAICompletion.choices[0].text)
        assertEquals(0, openAICompletion.choices[0].index)
        assertEquals("", openAICompletion.choices[0].finishReason)
        assertEquals(
            Usage(
                promptTokens = null,
                completionTokens = 10,
                totalTokens = 10
            ), openAICompletion.usage
        )
    }

    @Test
    fun `should convert OllamaGenerateResponse to OpenAICompletion with all fields`() {
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
    fun `should convert OllamaChatResponse to OpenAIChatCompletion without message`() {
        val ollamaChatResponse = OllamaChatResponse(
            createdAt = "123",
            model = "llama2",
            done = false
        )
        val openAIChatCompletion = ollamaChatResponse.toOpenAIChatCompletion()
        assertEquals("123", openAIChatCompletion.id)
        assertEquals(1L, openAIChatCompletion.created)
        assertEquals("llama2", openAIChatCompletion.model)
        assertEquals(0, openAIChatCompletion.choices.size)
    }

    @Test
    fun `should convert OllamaChatResponse to OpenAIChatCompletion with all fields`() {
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
    fun `should throw IllegalArgumentException when message not recognized`() {
        val chatCompletionRequest = ChatCompletionRequest(
            model = OpenAIModel(OllamaModel.LLAMA2.value),
            messages = listOf(
                ChatMessage.vision(emptyList())
            )
        )

        assertThrows(IllegalArgumentException::class.java) {
            chatCompletionRequest.toOllamaChatRequest()
        }
    }

    @Test
    fun `should throw IllegalArgumentException when role not recognized`() {
        val chatCompletionRequest = ChatCompletionRequest(
            model = OpenAIModel(OllamaModel.LLAMA2.value),
            messages = listOf(
                ChatMessage.UserMessage(
                    content = "Hello",
                    role = Role.Tool
                )
            )
        )

        assertThrows(IllegalArgumentException::class.java) {
            chatCompletionRequest.toOllamaChatRequest()
        }
    }

    @Test
    fun `should convert ChatCompletionRequest to OllamaChatRequest`() {
        val chatCompletionRequest = ChatCompletionRequest(
            model = OpenAIModel(OllamaModel.LLAMA2.value),
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
