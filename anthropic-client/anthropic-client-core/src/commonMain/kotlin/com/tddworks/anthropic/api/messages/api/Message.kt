package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.Serializable

/**
 * https://docs.anthropic.com/claude/reference/messages_post
 * Our models are trained to operate on alternating user and assistant conversational turns. When creating a new Message, you specify the prior conversational turns with the messages parameter, and the model then generates the next Message in the conversation.
 *
 * Each input message must be an object with a role and content. You can specify a single user-role message, or you can include multiple user and assistant messages. The first message must always use the user role.
 *
 * If the final message uses the assistant role, the response content will continue immediately from the content in that message. This can be used to constrain part of the model's response.
 *
 * Example with a single user message:
 *
 * [{"role": "user", "content": "Hello, Claude"}]
 */
@Serializable
data class Message(
    val role: Role,
    val content: String,
) {
    companion object {
        fun user(content: String) = Message(Role.User, content)
    }
}