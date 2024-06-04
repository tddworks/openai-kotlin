package com.tddworks.anthropic.api.messages.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class DeltaTest {

    @Test
    fun `should create dummy Delta`() {
        val delta = Delta.dummy()
        assertEquals("text_delta", delta.type)
        assertEquals("Hello", delta.text)
        assertEquals("end_turn", delta.stopReason)
        assertEquals(null, delta.stopSequence)
        assertEquals(Usage(outputTokens = 15), delta.usage)
    }

}