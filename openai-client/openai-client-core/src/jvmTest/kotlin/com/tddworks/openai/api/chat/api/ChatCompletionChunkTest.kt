package com.tddworks.openai.api.chat.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChatCompletionChunkTest {

    @Test
    fun `should create dummy chunk`() {
        val chunk = ChatCompletionChunk.dummy()
        assertEquals("fake-id", chunk.id)
        assertEquals("text", chunk.`object`)
        assertEquals(0, chunk.created)
        assertEquals("fake-model", chunk.model)
        assertEquals(1, chunk.choices.size)
        assertEquals(ChatChunk.fake(), chunk.choices[0])
        assertEquals("fake-content", chunk.content())
    }
}
