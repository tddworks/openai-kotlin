package com.tddworks.openai.gateway.api

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.Model
import com.tddworks.anthropic.api.messages.api.*
import com.tddworks.openai.api.chat.api.*
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.Role as OpenAIRole
import com.tddworks.openai.api.chat.api.Model as OpenAIModel
import com.tddworks.openai.api.chat.api.ChatCompletionChunk as OpenAIChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletion as OpenAIChatCompletion
import com.tddworks.openai.api.chat.api.ChatMessage.UserMessage as OpenAIUserMessage
import com.tddworks.openai.api.chat.api.ChatMessage.AssistantMessage as OpenAIAssistantMessage
import com.tddworks.openai.api.chat.api.ChatCompletionRequest as OpenAIChatCompletionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.transform


class AnthropicOpenAIProvider(private val client: Anthropic) : OpenAIProvider {
    override fun supports(model: OpenAIModel): Boolean {
        return Model.availableModels.any { it.value == model.value }
    }

    override suspend fun completions(request: ChatCompletionRequest): OpenAIChatCompletion {
        val anthropicRequest = request.toAnthropicRequest()
        return client.create(anthropicRequest).let {
            it.toOpenAIChatCompletion()
        }
    }

    override fun streamCompletions(request: ChatCompletionRequest): Flow<OpenAIChatCompletionChunk> {
        return client.stream(request.toAnthropicRequest() as StreamMessageRequest)
            .filter { it !is ContentBlockStop && it !is Ping }
            .transform {
                emit(it.toOpenAIChatCompletionChunk(request.model.value))
            }
    }

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
    private fun StreamMessageResponse.toOpenAIChatCompletionChunk(model: String): OpenAIChatCompletionChunk {
        val id = "chatcmpl-123"
        val created = 1L

        val chatChunkList = listOf(
            ChatChunk(
                index = 0,
                delta = ChatDelta(role = OpenAIRole.Assistant),
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


    private fun OpenAIChatCompletionRequest.toAnthropicRequest(): CreateMessageRequest {
        return CreateMessageRequest(
            model = Model(model.value),
            messages = messages.filter { it.role == OpenAIRole.User }.map {
                Message(
                    content = (it as OpenAIUserMessage).content,
                    role = Role.User
                )
            }
        )
    }

    private fun CreateMessageResponse.toOpenAIChatCompletion(): OpenAIChatCompletion {
        return OpenAIChatCompletion(
            id = id,
            created = 1L,
            model = model,
            choices = content.map {
                ChatChoice(
                    message = OpenAIAssistantMessage(
                        content = it.text,
                        role = OpenAIRole.Assistant
                    ),
                    index = 0,
                )
            }
        )
    }

    private fun mapAnthropicStopReason(finishReason: String?): OpenAIStopReason {
        return when (finishReason) {
            "end_turn", "stop_sequence", "message_stop" -> OpenAIStopReason.Stop
            "tool_use" -> OpenAIStopReason.ToolCalls
            "max_tokens" -> OpenAIStopReason.Length
            else -> OpenAIStopReason.Other
        }
    }

    enum class OpenAIStopReason {
        Stop,
        Length,
        FunctionCall,
        ToolCalls,
        Other
    }
}