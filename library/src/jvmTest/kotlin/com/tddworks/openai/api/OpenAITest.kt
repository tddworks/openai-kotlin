package com.tddworks.openai.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OpenAITest {

    @Test
    fun `should return correct base url`() {
        assertEquals("api.openai.com", OpenAI.BASE_URL)
    }
}