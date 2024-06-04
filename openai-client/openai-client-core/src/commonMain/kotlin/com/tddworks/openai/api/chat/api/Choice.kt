package com.tddworks.openai.api.chat.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Data class representing a chat completion option.
 *
 * @property delta The difference between the previous message and the current message.
 * @property index The index of the completion option (used for selecting a specific option).
 * @property finishReason The reason for completing this response (e.g. "max_tokens").
 */
@Serializable
data class ChatChunk(
    val delta: ChatDelta,
    val index: Int,
    val logprobs: String? = null,
    @SerialName("finish_reason")
    val finishReason: String?,
) {
    companion object {
        fun fake() = ChatChunk(
            delta = ChatDelta.fake(),
            index = 0,
            finishReason = null,
        )
    }

}

/**
 * Data class representing the difference between the previous message and the current message.
 *
 * @property content The content of the message.
 * @property chatRole The role of the message sender (user or assistant).
 */
@Serializable
data class ChatDelta(
    val content: String? = null,
    val role: Role? = null,
) {
    companion object {
        fun fake() = ChatDelta(
            content = "fake-content",
            role = Role.Assistant,
        )
    }

}

@Serializable
data class ChatChoice(
    /**
     * Chat choice index.
     */
    @SerialName("index") val index: Int,
    /**
     * The generated chat message.
     */
    @Serializable(with = ChatMessageSerializer::class)
    @SerialName("message") val message: ChatMessage,

    /**
     * The reason why OpenAI stopped generating.
     */
    @SerialName("finish_reason") val finishReason: FinishReason? = null,
) {
    companion object {
        fun dummy() = ChatChoice(
            index = 0,
            message = ChatMessage.SystemMessage("Hello! How can I assist you today?"),
            finishReason = FinishReason.Stop
        )
    }
}

@Serializable
@JvmInline
value class FinishReason(val value: String) {
    companion object {
        val Stop: FinishReason = FinishReason("stop")
        val Length: FinishReason = FinishReason("length")
        val FunctionCall: FinishReason = FinishReason("function_call")
        val ToolCalls: FinishReason = FinishReason("tool_calls")
    }
}