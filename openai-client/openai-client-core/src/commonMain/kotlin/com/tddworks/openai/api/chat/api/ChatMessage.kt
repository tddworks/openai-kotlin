package com.tddworks.openai.api.chat.api

import com.tddworks.openai.api.chat.api.vision.VisionMessageContent
import kotlinx.serialization.*
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


/**
 * Data class representing a user message.
 *
 * @property content The content of the message.
 * @property role The role of the message sender (user or assistant). Default is USER.
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable(with = ChatMessageSerializer::class)
sealed interface ChatMessage {
    val role: Role
    val content: Any

    companion object {
        fun system(content: String) = SystemMessage(content)
        fun user(content: String) = UserMessage(content)
        fun assistant(content: String) = AssistantMessage(content)

        fun vision(content: List<VisionMessageContent>) = VisionMessage(content)
    }

    @Serializable
    data class SystemMessage(
        @SerialName("content")
        override val content: String,
        @SerialName("role")
        @EncodeDefault(EncodeDefault.Mode.ALWAYS)
        override val role: Role = Role.System,
    ) : ChatMessage

    @Serializable
    data class UserMessage(
        @SerialName("content")
        override val content: String,
        @EncodeDefault(EncodeDefault.Mode.ALWAYS)
        @SerialName("role")
        override val role: Role = Role.User,
    ) : ChatMessage

    @Serializable
    data class AssistantMessage(
        @SerialName("content")
        override val content: String,
        @SerialName("role")
        @EncodeDefault(EncodeDefault.Mode.ALWAYS)
        override val role: Role = Role.Assistant,
    ) : ChatMessage

    /**
     * @see [Learn how to use GPT-4 to understand images](https://platform.openai.com/docs/guides/vision)
     */
    @Serializable
    data class VisionMessage(
        /**
         * The contents of the tool message.
         */
        @SerialName("content")
        override val content: List<VisionMessageContent>,
        /**
         * The role of the messages author, in this case tool.
         */
        @EncodeDefault(EncodeDefault.Mode.ALWAYS)
        @SerialName("role")
        override val role: Role = Role.User,
    ) : ChatMessage
}

object ChatMessageSerializer :
    JsonContentPolymorphicSerializer<ChatMessage>(ChatMessage::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out ChatMessage> {
        val type = element.jsonObject["role"]?.jsonPrimitive?.content

        return when (type) {
            Role.System.name -> ChatMessage.SystemMessage.serializer()
            Role.User.name -> ChatMessage.UserMessage.serializer()
            Role.Assistant.name -> ChatMessage.AssistantMessage.serializer()
            else -> throw IllegalArgumentException("Unknown type")
        }
    }
}