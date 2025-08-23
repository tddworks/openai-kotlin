package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://docs.anthropic.com/claude/reference/messages_post Our models are trained to operate on
 * alternating user and assistant conversational turns. When creating a new Message, you specify the
 * prior conversational turns with the messages parameter, and the model then generates the next
 * Message in the conversation.
 *
 * Each input message must be an object with a role and content. You can specify a single user-role
 * message, or you can include multiple user and assistant messages. The first message must always
 * use the user role.
 *
 * If the final message uses the assistant role, the response content will continue immediately from
 * the content in that message. This can be used to constrain part of the model's response.
 *
 * Example with a single user message:
 *
 * [{"role": "user", "content": "Hello, Claude"}]
 *
 * Each input message content may be either a single string or an array of content blocks, where
 * each block has a specific type. Using a string for content is shorthand for an array of one
 * content block of type "text". The following input messages are equivalent: {"role": "user",
 * "content": "Hello, Claude"} {"role": "user", "content":
 * [{"type": "text", "text": "Hello, Claude"}]}
 */
@Serializable
data class Message(val role: Role, val content: Content) {
    companion object {
        fun user(content: String) = Message(Role.User, Content.TextContent(content))
    }
}

@Serializable(with = ContentSerializer::class)
sealed interface Content {
    data class TextContent(val text: String) : Content

    data class BlockContent(val blocks: List<BlockMessageContent>) : Content
}

/**
 * https://docs.anthropic.com/en/docs/build-with-claude/vision#prompt-examples { "role": "user",
 * "content":
 * [ { "type": "image", "source": { "type": "base64", "media_type": image1_media_type, "data": image1_data, }, }, { "type": "text", "text": "Describe this image." } ],
 * }
 */
@Serializable
sealed interface BlockMessageContent {

    @Serializable
    @SerialName("image")
    data class ImageContent(val source: Source) : BlockMessageContent {
        @Serializable
        data class Source(
            @SerialName("media_type") val mediaType: String,
            val data: String,
            val type: String,
        )
    }

    @Serializable
    @SerialName("text")
    data class TextContent(val text: String) : BlockMessageContent
}
