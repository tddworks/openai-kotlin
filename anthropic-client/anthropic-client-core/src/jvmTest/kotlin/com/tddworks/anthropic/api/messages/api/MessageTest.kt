package com.tddworks.anthropic.api.messages.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MessageTest {

    @Test
    fun `should return assistant message name`() {
        val assistantMessage = Message(role = Role.Assistant, content = "message")
        assertEquals(Role.Assistant, assistantMessage.role)
        assertEquals("message", assistantMessage.content)
    }

    @Test
    fun `should return user message name`() {
        val userMessage = Message.user("message")
        assertEquals(Role.User, userMessage.role)
        assertEquals("message", userMessage.content)
    }

}
