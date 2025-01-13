package com.tddworks.anthropic.api.messages.api

import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.openai.api.chat.api.ChatChoice
import com.tddworks.openai.api.chat.api.ChatChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.ChatDelta
import com.tddworks.openai.api.chat.api.Role.Companion.Assistant
import kotlinx.serialization.ExperimentalSerializationApi
import com.tddworks.openai.api.chat.api.ChatCompletion as OpenAIChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk as OpenAIChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatMessage.AssistantMessage as OpenAIAssistantMessage
import com.tddworks.openai.api.chat.api.ChatMessage.UserMessage as OpenAIUserMessage
import com.tddworks.openai.api.chat.api.ChatMessage.SystemMessage as OpenAISystemMessage
import com.tddworks.openai.api.chat.api.Role as OpenAIRole

/**
 * MessageStart(type=message_start, message=CreateMessageResponse(content=[], id=msg_01SYdcxEGs5xE222wXncY3g3, model=claude-3-haiku-20240307, role=assistant, stopReason=null, stopSequence=null, type=message, usage=Usage(inputTokens=8, outputTokens=1)))
 * ContentBlockStart(type=content_block_start, index=0, contentBlock=ContentBlock(type=text, text=))
 * Ping(type=ping)
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text=Hello, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text=!, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text= How, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text= can, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text= I, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text= assist, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text= you, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text= today, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockDelta(type=content_block_delta, index=0, delta=Delta(type=text_delta, text=?, stopReason=null, stopSequence=null, usage=null))
 * ContentBlockStop(type=content_block_stop, index=0)
 * MessageDelta(type=message_delta, delta=Delta(type=null, text=null, stopReason=end_turn, stopSequence=null, usage=null))
 * MessageStop(type=message_stop)
 *
 * ===================
 * to openai response
 * ===================
 *
 * {"id":"chatcmpl-123","object":"chat.completion.chunk","created":1694268190,"model":"gpt-3.5-turbo-0125", "system_fingerprint": "fp_44709d6fcb", "choices":[{"index":0,"delta":{"role":"assistant","content":""},"logprobs":null,"finish_reason":null}]}
 *
 * {"id":"chatcmpl-123","object":"chat.completion.chunk","created":1694268190,"model":"gpt-3.5-turbo-0125", "system_fingerprint": "fp_44709d6fcb", "choices":[{"index":0,"delta":{"content":"Hello"},"logprobs":null,"finish_reason":null}]}
 *
 * ....
 *
 * {"id":"chatcmpl-123","object":"chat.completion.chunk","created":1694268190,"model":"gpt-3.5-turbo-0125", "system_fingerprint": "fp_44709d6fcb", "choices":[{"index":0,"delta":{},"logprobs":null,"finish_reason":"stop"}]}
 *
 */
fun StreamMessageResponse.toOpenAIChatCompletionChunk(model: String): OpenAIChatCompletionChunk {
    val id = "chatcmpl-123"
    val created = 1L

    val chatChunkList = listOf(
        ChatChunk(
            index = 0,
            delta = ChatDelta(role = Assistant),
            finishReason = when (this) {
                is MessageStart, is ContentBlockStart, is ContentBlockDelta -> null
                is MessageDelta -> delta.stopReason?.let { mapAnthropicStopReason(it).name }
                is MessageStop -> mapAnthropicStopReason(type).name
                else -> throw IllegalArgumentException("Unknown message type: $this")
            }
        )
    )

    return OpenAIChatCompletionChunk(
        id = id,
        `object` = type,
        created = created,
        model = model,
        choices = chatChunkList.map { chatChunk ->
            when (this) {
                is ContentBlockStart -> chatChunk.copy(
                    delta = chatChunk.delta.copy(content = contentBlock.text)
                )

                is ContentBlockDelta -> chatChunk.copy(
                    delta = chatChunk.delta.copy(content = delta.text)
                )

                else -> chatChunk
            }
        }
    )
}


@OptIn(ExperimentalSerializationApi::class)
fun ChatCompletionRequest.toAnthropicStreamRequest(): CreateMessageRequest {
    return toAnthropicRequest(true)
}


@OptIn(ExperimentalSerializationApi::class)
fun ChatCompletionRequest.toAnthropicRequest(stream: Boolean? = null): CreateMessageRequest {
    val systemPrompt =
        messages.firstOrNull { it.role == OpenAIRole.System }?.content as? String

    val formattedMessages = messages
        .filterNot { it.role == OpenAIRole.System }
        .map { message ->
            Message(
                content = when (message) {
                    is OpenAIUserMessage -> Content.TextContent(message.content)
                    is OpenAIAssistantMessage -> Content.TextContent(message.content)
                    is OpenAISystemMessage -> Content.TextContent(message.content)
                    else -> throw IllegalArgumentException("Unknown message type: $message")
                },
                role = when (message.role) {
                    OpenAIRole.User -> Role.User
                    OpenAIRole.Assistant -> Role.Assistant
                    else -> throw IllegalArgumentException("Unknown role: ${message.role}")
                }
            )
        }

    return CreateMessageRequest(
        model = AnthropicModel(model.value),
        systemPrompt = systemPrompt,
        messages = formattedMessages,
        stream = stream
    )
}

fun CreateMessageResponse.toOpenAIChatCompletion(): OpenAIChatCompletion {
    return OpenAIChatCompletion(
        id = id,
        created = 1L,
        model = model,
        choices = content.map {
            ChatChoice(
                message = OpenAIAssistantMessage(
                    content = it.text,
                    role = Assistant
                ),
                index = 0,
            )
        }
    )
}

fun mapAnthropicStopReason(finishReason: String?): OpenAIStopReason {
    return when (finishReason) {
        "end_turn", "stop_sequence", "message_stop" -> OpenAIStopReason.Stop
        "tool_use" -> OpenAIStopReason.ToolCalls
        "max_tokens" -> OpenAIStopReason.Length
        else -> OpenAIStopReason.Other
    }
}