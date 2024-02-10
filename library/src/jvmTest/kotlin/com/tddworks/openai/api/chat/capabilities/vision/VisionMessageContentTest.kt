package com.tddworks.openai.api.chat.capabilities.vision

import com.tddworks.openai.api.prettyJson
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VisionMessageContentTest {
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
            expectedJson,
            prettyJson.encodeToString(imageContent)
        )
    }

    @Test
    fun `should return correct json for text content`() {
        val textContent = VisionMessageContent.TextContent(content = "Hello, how may I assist you today?")
        val expectedJson = """
            {
              "type": "text",
              "text": "Hello, how may I assist you today?"
            }
        """.trimIndent()

        assertEquals(expectedJson, prettyJson.encodeToString(textContent))
    }
}