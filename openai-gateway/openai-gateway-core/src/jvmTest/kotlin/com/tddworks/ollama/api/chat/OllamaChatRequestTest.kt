package com.tddworks.ollama.api.chat

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OllamaChatRequestTest {

    @Test
    fun `should return dummy request`() {
        // given
        val request = OllamaChatRequest.dummy()

        // then
        assertEquals("llama2", request.model)
        assertEquals(1, request.messages.size)
        assertEquals("user", request.messages[0].role)
        assertEquals("Hello!", request.messages[0].content)
    }

}