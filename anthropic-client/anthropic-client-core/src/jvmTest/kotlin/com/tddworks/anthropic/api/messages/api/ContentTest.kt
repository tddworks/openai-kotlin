package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Each input message content may be either a single string or an array of content blocks, where
 * each block has a specific type. Using a string for content is shorthand for an array of one
 * content block of type "text". The following input messages are equivalent: {"role": "user",
 * "content": "Hello, Claude"} {"role": "user", "content":
 * [{"type": "text", "text": "Hello, Claude"}]}
 */
class ContentTest {

    @Test
    fun `should serialize multiple content`() {
        // Given
        val content =
            Content.BlockContent(
                listOf(
                    BlockMessageContent.ImageContent(
                        source =
                            BlockMessageContent.ImageContent.Source(
                                mediaType = "image1_media_type",
                                data = "image1_data",
                                type = "base64",
                            )
                    ),
                    BlockMessageContent.TextContent(text = "some-text"),
                )
            )

        // When
        val result = Json.encodeToString(Content.serializer(), content)

        // Then
        JSONAssert.assertEquals(
            """
            [
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
            """
                .trimIndent(),
            result,
            false,
        )
    }

    @Test
    fun `should serialize single string content`() {
        // Given
        val content = Content.TextContent("Hello, Claude")

        // When
        val result = Json.encodeToString(Content.serializer(), content)

        // Then
        assertEquals(
            """
            "Hello, Claude"
            """
                .trimIndent(),
            result,
        )
    }
}
