package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UsageTest {
    @Test
    fun `should create a empty Usage`() {
        val usage = Usage()
        val json = Json.encodeToString(usage)

        val expectedJson = "{}"
        assertEquals(expectedJson, json)
    }

    @Test
    fun `should create a dummy Usage`() {
        val usage = Usage(inputTokens = 10, outputTokens = 20)
        val json = Json.encodeToString(usage)

        val expectedJson = "{\"input_tokens\":10,\"output_tokens\":20}"
        assertEquals(expectedJson, json)
    }

    @Test
    fun `should parse a Usage from json`() {
        val json = "{\"input_tokens\":5,\"output_tokens\":15}"
        val usage = Json.decodeFromString<Usage>(json)

        assertEquals(5, usage.inputTokens)
        assertEquals(15, usage.outputTokens)
    }
}
