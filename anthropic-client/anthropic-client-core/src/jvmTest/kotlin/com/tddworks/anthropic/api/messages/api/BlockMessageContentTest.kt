package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class BlockMessageContentTest {

    @Test
    fun `should serialize image message content`() {
        // Given
        val messageContent = BlockMessageContent.ImageContent(
            source = BlockMessageContent.ImageContent.Source(
                mediaType = "image1_media_type",
                data = "image1_data",
                type = "base64",
            ),
        )

        // When
        val result = Json.encodeToString(
            BlockMessageContent.serializer(),
            messageContent
        )

        // Then
        JSONAssert.assertEquals(
            """
            {
                "type": "image",
                "source": {
                    "type": "base64",
                    "media_type": "image1_media_type",
                    "data": "image1_data"
                }
            }
            """.trimIndent(),
            result,
            false
        )
    }

    @Test
    fun `should serialize message content`() {
        // Given
        val messageContent = BlockMessageContent.TextContent(
            text = "some-text",
        )

        // When
        val result = Json.encodeToString(
            BlockMessageContent.serializer(),
            messageContent
        )

        // Then
        JSONAssert.assertEquals(
            """
            {   
                "text": "some-text",
                "type": "text"
            }
            """.trimIndent(),
            result,
            false
        )
    }
}