package com.tddworks.anthropic.api.messages.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

/**
 * https://docs.anthropic.com/claude/reference/messages_post Our models are trained to operate on
 * alternating user and assistant conversational turns. When creating a new Message, you specify the
 * prior conversational turns with the messages parameter, and the model then generates the next
 * Message in the conversation.
 */
@JvmInline
@Serializable
value class Role(val name: String) {
    companion object {
        val User: Role = Role("user")
        val Assistant: Role = Role("assistant")
    }
}
