package com.tddworks.anthropic.api.messages.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ContentMessageTest {

    @Test
    fun `should create a ContentMessage`() {
        val dummyMessage = ContentMessage("Hi! My name is Claude.", "text")

        assertEquals("Hi! My name is Claude.", dummyMessage.text)
        assertEquals("text", dummyMessage.type)
    }
}