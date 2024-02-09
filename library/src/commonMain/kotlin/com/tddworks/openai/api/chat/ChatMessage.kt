package com.tddworks.openai.api.chat

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a user message.
 *
 * @property content The content of the message.
 * @property role The role of the message sender (user or assistant). Default is USER.
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class ChatMessage(
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    @SerialName("content")
    val content: String,
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    @SerialName("role")
    val role: Role = Role.USER,
) {
    companion object {
        fun system(content: String) = ChatMessage(content, Role.SYSTEM)
        fun user(content: String) = ChatMessage(content, Role.USER)
    }
}