package com.tddworks.openai.api.chat

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

/**
 * Data class representing the settings for generating a chat completion.
 *
 * @property messages The user's message.
 * @property model The GPT model to use for generating the chat completion. Default is GPT_3_5_TURBO.
 * @property stream Whether to use streaming or not. Default is true.
 */
@Serializable
@ExperimentalSerializationApi
data class ChatCompletionRequest(
    @SerialName("messages")
    val messages: List<ChatMessage>,
    /**
     * Controls whether the target property is serialized when its value is equal to a default value,
     * regardless of the format settings.
     * Does not affect decoding and deserialization process.
     *
     * Example of usage:
     * ```
     * @Serializable
     * data class Foo(
     *     @EncodeDefault(ALWAYS) val a: Int = 42,
     *     @EncodeDefault(NEVER) val b: Int = 43,
     *     val c: Int = 44
     * )
     *
     * Json { encodeDefaults = false }.encodeToString((Foo()) // {"a": 42}
     * Json { encodeDefaults = true }.encodeToString((Foo())  // {"a": 42, "c":44}
     * ```
     *
     * @see EncodeDefault.Mode.ALWAYS
     * @see EncodeDefault.Mode.NEVER
     */
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    val model: Model = Model.GPT_3_5_TURBO,
) : StreamableRequest {
    companion object {

        fun chatCompletionRequest(
            messages: List<ChatMessage>,
            model: Model = Model.GPT_3_5_TURBO,
        ): ChatCompletionRequest {
            return ChatCompletionRequest(
                messages = messages,
                model = model
            )
        }

        fun chatCompletionsRequest(
            messages: List<ChatMessage>,
            model: Model = Model.GPT_3_5_TURBO,
        ): ChatCompletionRequest {
            return ChatCompletionRequest(
                messages = messages,
                model = model
            )
        }

        /**
         * Convenience function to create a ComplicationRequest from a string question.
         *
         * @param question The user's question.
         * @return A ComplicationRequest object.
         */
        fun from(question: String): ChatCompletionRequest {
            return ChatCompletionRequest(
                listOf(ChatMessage(question)),
            )
        }

        fun from(question: String, model: Model): ChatCompletionRequest {
            return ChatCompletionRequest(
                messages = listOf(ChatMessage(question)),
                model = model,
            )
        }

        /**
         * Creates a dummy ComplicationRequest for testing purposes.
         *
         * @return A ComplicationRequest object with a single dummy message.
         */
        fun dummy(): ChatCompletionRequest {
            return ChatCompletionRequest(
                listOf(ChatMessage("dummy-content")),
            )
        }
    }
}
