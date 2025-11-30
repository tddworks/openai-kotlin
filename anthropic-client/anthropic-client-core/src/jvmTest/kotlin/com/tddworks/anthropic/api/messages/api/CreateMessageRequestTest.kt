package com.tddworks.anthropic.api.messages.api

import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.anthropic.api.prettyJson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * https://docs.anthropic.com/en/api/messages Each input message content may be either a single
 * string or an array of content blocks, where each block has a specific type. Using a string for
 * content is shorthand for an array of one content block of type "text". The following input
 * messages are equivalent: {"role": "user", "content": "Hello, Claude"} {"role": "user", "content":
 * [{"type": "text", "text": "Hello, Claude"}]}
 */
class CreateMessageRequestTest {

    @Test
    fun `should convert request to correct json with multiple messages`() {
        val json =
            """
               {
                "messages": [{
                    "role": "user",
                    "content": [{
                            "type": "image",
                            "source": {
                                "type": "base64",
                                "media_type": "123",
                                "data": "23"
                            }
                        },
                        {
                            "type": "text",
                            "text": "Describe this image."
                        }
                    ]
                }],
                "system": null,
                "max_tokens": 1024,
                "model": "claude-3-haiku-20240307",
                "stream": null
            }
            """
                .trimIndent()

        val request =
            CreateMessageRequest(
                messages =
                    listOf(
                        Message(
                            role = Role.User,
                            content =
                                Content.BlockContent(
                                    listOf(
                                        BlockMessageContent.ImageContent(
                                            source =
                                                BlockMessageContent.ImageContent.Source(
                                                    mediaType = "123",
                                                    data = "23",
                                                    type = "base64",
                                                )
                                        ),
                                        BlockMessageContent.TextContent(
                                            text = "Describe this image."
                                        ),
                                    )
                                ),
                        )
                    ),
                maxTokens = 1024,
                model = AnthropicModel.CLAUDE_3_HAIKU,
            )

        JSONAssert.assertEquals(
            json,
            prettyJson.encodeToString(CreateMessageRequest.serializer(), request),
            false,
        )
    }

    @Test
    fun `should convert request to correct json`() {
        val json =
            """
            {
              "messages": [
                {
                  "role": "user",
                  "content": "hello"
                }
              ],
              "system": null,
              "max_tokens": 1024,
              "model": "claude-3-haiku-20240307",
              "stream": null
            }
            """
                .trimIndent()

        val request =
            CreateMessageRequest(
                messages = listOf(Message.user("hello")),
                maxTokens = 1024,
                model = AnthropicModel.CLAUDE_3_HAIKU,
            )

        assertEquals(json, prettyJson.encodeToString(CreateMessageRequest.serializer(), request))
    }
}
