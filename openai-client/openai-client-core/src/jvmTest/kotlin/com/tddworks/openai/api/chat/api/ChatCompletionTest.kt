package com.tddworks.openai.api.chat.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChatCompletionTest {

    @Test
    fun `should return correct dummy chatCompletion`() {
        val chatCompletion = ChatCompletion.dummy()

        assertEquals("chatcmpl-8Zu4AF8QMK3zFgdzXIPjFS4VkWErX", chatCompletion.id)
        assertEquals(1634160000, chatCompletion.created)
        assertEquals("gpt-3.5-turbo", chatCompletion.model)
        assertEquals(1, chatCompletion.choices.size)
        assertEquals(ChatChoice.dummy(), chatCompletion.choices[0])
    }
}
