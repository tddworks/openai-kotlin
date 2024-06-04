package com.tddworks.anthropic.api.messages.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class CreateMessageResponseTest {

    @Test
    fun `should create a dummy CreateMessageResponse`() {

        val dummyResponse = CreateMessageResponse.dummy()

        assertEquals("msg_1nZdL29xx5MUA1yADyHTEsnR8uuvGzszyY", dummyResponse.id)
        assertEquals("claude-3-opus-20240229", dummyResponse.model)
        assertEquals("assistant", dummyResponse.role)
        assertNull(dummyResponse.stopReason)
        assertNull(dummyResponse.stopSequence)
        assertEquals("message", dummyResponse.type)
        assertEquals(Usage(25, 1), dummyResponse.usage)
        assertEquals(
            listOf(ContentMessage("Hi! My name is Claude.", "text")),
            dummyResponse.content
        )
    }

}