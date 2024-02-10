package com.tddworks.openai.api

import com.tddworks.openai.api.chat.ChatCompletionRequest
import com.tddworks.openai.api.chat.ChatMessage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class OpenAITest {

    @Test
    fun `should return correct base url`() {
        assertEquals("api.openai.com", OpenAI.BASE_URL)
    }

    @Disabled
    @Test
    fun `should return response`() = runBlocking {
        val openAI = OpenAI("api-key")
        val response = openAI.streamCompletions(
            ChatCompletionRequest(
                messages = listOf(
                    ChatMessage.user("hello")
                )
            )
        ).toList().first().content()
        assertEquals("Hello! How can I assist you today?", response)
    }
}