package com.tddworks.openai.api.chat.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a chat completion.
 *
 * @property id The ID of the chat completion.
 * @property `object` The type of object returned (always "text").
 * @property created The timestamp of when the completion was created.
 * @property model The name of the GPT model used to generate the completion.
 * @property choices A list of possible chat completion options.
 */
@Serializable
data class ChatCompletionChunk(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    @SerialName("system_fingerprint")
    val systemFingerprint: String? = null,
    val choices: List<ChatChunk>,
) {
    companion object {
        fun dummy() = ChatCompletionChunk(
            id = "fake-id",
            `object` = "text",
            created = 0,
            model = "fake-model",
            choices = listOf(ChatChunk.fake()),
        )
    }

    fun content() = choices.joinToString(separator = "") { it.delta.content ?: "" }
}