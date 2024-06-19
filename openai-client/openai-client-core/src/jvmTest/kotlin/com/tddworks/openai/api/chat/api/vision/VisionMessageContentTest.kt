package com.tddworks.openai.api.chat.api.vision

import com.tddworks.openai.api.common.prettyJson
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VisionMessageContentTest {

    @Test
    fun `should throw IllegalArgumentException when type was not jsonPrimitive`() {
        val json = """
            {
                "type": ["text"],
                "text": "Hello, how may I assist you today?"
            }
        """.trimIndent()

        assertThrows<IllegalArgumentException> {
            prettyJson.decodeFromString(
                VisionMessageContent.serializer(), json
            )
        }
    }


    @Test
    fun `should throw IllegalArgumentException for unknown json`() {
        val json = """
            {
              "text": "Hello, how may I assist you today?"
            }
        """.trimIndent()

        assertThrows<IllegalArgumentException> {
            prettyJson.decodeFromString(
                VisionMessageContent.serializer(), json
            )
        }
    }

    @Test
    fun `should throw IllegalArgumentException for unknown type`() {
        val json = """
            {
              "type": "unknown",
              "text": "Hello, how may I assist you today?"
            }
        """.trimIndent()

        assertThrows<IllegalArgumentException> {
            prettyJson.decodeFromString(
                VisionMessageContent.serializer(), json
            )
        }
    }

    @Test
    fun `should convert json to image VisionMessageContent`() {
        val json = """
            {
              "type": "image_url",
              "image_url": {
                "url": "https://example.com/image.jpg",
                "detail": "auto"
              }
            }
        """.trimIndent()

        val visionMessageContent =
            prettyJson.decodeFromString(VisionMessageContent.serializer(), json)

        assertEquals(
            VisionMessageContent.ImageContent(
                imageUrl = ImageUrl("https://example.com/image.jpg")
            ), visionMessageContent
        )
    }

    @Test
    fun `should convert json to text VisionMessageContent`() {
        val json = """
            {
              "type": "text",
              "text": "Hello, how may I assist you today?"
            }
        """.trimIndent()

        val visionMessageContent =
            prettyJson.decodeFromString(VisionMessageContent.serializer(), json)

        assertEquals(
            VisionMessageContent.TextContent(content = "Hello, how may I assist you today?"),
            visionMessageContent
        )
    }


    @Test
    fun `should return correct json for image content`() {
        val imageUrl = ImageUrl("https://example.com/image.jpg")
        val imageContent = VisionMessageContent.ImageContent(imageUrl = imageUrl)
        val expectedJson = """
        {
          "type": "image_url",
          "image_url": {
            "url": "https://example.com/image.jpg",
            "detail": "auto"
          }
        }
        """.trimIndent()

        assertEquals(
            expectedJson, prettyJson.encodeToString(imageContent)
        )
    }

    @Test
    fun `should return correct json for text content`() {
        val textContent =
            VisionMessageContent.TextContent(content = "Hello, how may I assist you today?")
        val expectedJson = """
            {
              "type": "text",
              "text": "Hello, how may I assist you today?"
            }
        """.trimIndent()

        assertEquals(expectedJson, prettyJson.encodeToString(textContent))
    }
}