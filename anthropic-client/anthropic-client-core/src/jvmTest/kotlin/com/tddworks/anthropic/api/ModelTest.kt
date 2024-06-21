package com.tddworks.anthropic.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ModelTest {

    @Test
    fun `should return correct latest API model name`() {
        assertEquals("claude-3-opus-20240229", Model.CLAUDE_3_OPUS.value)
        assertEquals("claude-3-sonnet-20240229", Model.CLAUDE_3_Sonnet.value)
        assertEquals("claude-3-haiku-20240307", Model.CLAUDE_3_HAIKU.value)
        assertEquals("claude-3-5-sonnet-20240620", Model.CLAUDE_3_5_Sonnet.value)
    }
}