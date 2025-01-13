package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Each input message content may be either a single string or an array of content blocks, where each block has a specific type. Using a string for content is shorthand for an array of one content block of type "text". The following input messages are equivalent:
 *
 * {"role": "user", "content": "Hello, Claude"}
 * {"role": "user", "content": [{"type": "text", "text": "Hello, Claude"}]}
 */
class MessageTest {

    @Test
    fun `should serialize an array of content blocks`() {
        // Given
        val userMessage = Message(
            role = Role.User,
            content = Content.BlockContent(
                listOf(
                    BlockMessageContent.ImageContent(
                        source = BlockMessageContent.ImageContent.Source(
                            mediaType = "image1_media_type",
                            data = "image1_data",
                            type = "base64",
                        ),
                    ),
                    BlockMessageContent.TextContent(
                        text = "some-text",
                    )
                )
            )
        )

        // When
        val result = Json.encodeToString(
            Message.serializer(),
            userMessage
        )

        // Then
        JSONAssert.assertEquals(
            """
            {
                "role": "user",
                "content": [
                    {
                        "source": {
                            "data": "image1_data",
                            "media_type": "image1_media_type",
                            "type": "base64"
                        },
                        "type": "image"
                    },
                    {
                        "text": "some-text",
                        "type": "text"
                    }
                ]
            }
            """.trimIndent(),
            result,
            false
        )
    }

    @Test
    fun `should serialize single line message content`() {
        // Given
        val assistantMessage = Message(
            role = Role.Assistant,
            content = Content.TextContent(
                text = "message"
            )
        )

        // When
        val result = Json.encodeToString(
            Message.serializer(),
            assistantMessage
        )

        // Then
        JSONAssert.assertEquals(
            """
            {
                "role": "assistant",
                "content": "message"
            }
            """.trimIndent(),
            result,
            false
        )
    }

    @Test
    fun `should return assistant message name`() {
        val assistantMessage = Message(
            role = Role.Assistant,
            content = Content.TextContent(
                text = "message"
            )
        )
        assertEquals(Role.Assistant, assistantMessage.role)
        assertEquals(
            "message",
            (assistantMessage.content as Content.TextContent).text
        )
    }

    @Test
    fun `should return user message name`() {
        val userMessage = Message.user("message")
        assertEquals(Role.User, userMessage.role)
        assertEquals(
            "message",
            (userMessage.content as Content.TextContent).text
        )
    }

}
