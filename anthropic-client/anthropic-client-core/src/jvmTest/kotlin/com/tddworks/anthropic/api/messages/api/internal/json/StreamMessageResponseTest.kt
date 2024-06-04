package com.tddworks.anthropic.api.messages.api.internal.json

import com.tddworks.anthropic.api.messages.api.ContentBlockStop
import com.tddworks.anthropic.api.messages.api.StreamMessageResponse
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StreamMessageResponseTest {

    @Test
    fun `should throw IllegalArgumentException when type is null`() {
        val json = """
            {
                "type": "null"
            }
        """.trimIndent()

        val e = assertThrows(IllegalArgumentException::class.java) {
            Json.decodeFromString<StreamMessageResponse>(json)
        }

        assertEquals("Unknown type of message", e.message)
    }

    @Test
    fun `should throw IllegalArgumentException when type is not JsonPrimitive`() {
        val json = """
            {
                "type": {}
            }
        """.trimIndent()

        val e = assertThrows(IllegalArgumentException::class.java) {
            Json.decodeFromString<StreamMessageResponse>(json)
        }

        assertEquals("Element class kotlinx.serialization.json.JsonObject is not a JsonPrimitive", e.message)
    }

    @Test
    fun `should throw IllegalArgumentException when from invalid json`() {
        val json = """
            {
                "type": { "not-jsonPrimitive":"" }
            }
        """.trimIndent()

        assertThrows(IllegalArgumentException::class.java) {
            Json.decodeFromString<StreamMessageResponse>(json)
        }
    }

    @Test
    fun `should throw IllegalArgumentException when from json without type`() {
        val json = """
            {
                "index": 1
            }
        """.trimIndent()

        assertThrows(IllegalArgumentException::class.java) {
            Json.decodeFromString<StreamMessageResponse>(json)
        }
    }

    @Test
    fun `should return the ContentBlockStop from json`() {
        val json = """
            {
                "type": "content_block_stop",
                "index": 1
            }
        """.trimIndent()

        val contentBlockStop = Json.decodeFromString<StreamMessageResponse>(json)

        assertTrue(contentBlockStop is ContentBlockStop)
        assertEquals("content_block_stop", contentBlockStop.type)
        assertEquals(1, (contentBlockStop as ContentBlockStop).index)
    }

    @Test
    fun `should throw IllegalArgumentException for unknown message type`() {
        val json = """
            {
                "type": "unknown"
            }
        """.trimIndent()

        assertThrows(IllegalArgumentException::class.java) {
            Json.decodeFromString<StreamMessageResponse>(json)
        }
    }
}