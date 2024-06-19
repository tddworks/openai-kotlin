package com.tddworks.ollama.api.chat.api

import com.tddworks.ollama.api.chat.OllamaChatMessage
import com.tddworks.ollama.api.chat.OllamaChatRequest
import com.tddworks.ollama.api.chat.OllamaChatResponse
import com.tddworks.ollama.api.generate.OllamaGenerateRequest
import com.tddworks.ollama.api.generate.OllamaGenerateResponse
import com.tddworks.openai.api.chat.api.*
import com.tddworks.openai.api.chat.api.ChatMessage.*
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionChoice
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.api.legacy.completions.api.Usage
import kotlinx.serialization.ExperimentalSerializationApi


fun OllamaChatResponse.toOpenAIChatCompletion(): ChatCompletion {
    return ChatCompletion(
        id = createdAt,
        created = 1L,
        model = model,
        choices = listOf(
            ChatChoice(
                message = AssistantMessage(
                    content = message?.content ?: "",
                    role = when (message?.role) {
                        "user" -> Role.User
                        "assistant" -> Role.Assistant
                        "system" -> Role.System
                        else -> throw IllegalArgumentException("Unknown role: ${message?.role}")
                    }
                ),
                index = 0,
            )
        )
    )
}

fun OllamaChatResponse.toOpenAIChatCompletionChunk(): ChatCompletionChunk {
    val id = "chatcmpl-123"
    val created = 1L

    val chatChunkList = listOf(
        ChatChunk(
            index = 0,
            delta = ChatDelta(role = Role.Assistant),
            finishReason = if (done) "completed" else null
        )
    )

    return ChatCompletionChunk(
        id = id,
        `object` = "ollama-chunk",
        created = created,
        model = model,
        choices = chatChunkList.map { chatChunk ->
            chatChunk.copy(
                delta = chatChunk.delta.copy(
                    content = message?.content,
                )
            )
        }
    )
}

@OptIn(ExperimentalSerializationApi::class)
fun ChatCompletionRequest.toOllamaChatRequest(): OllamaChatRequest {
    return OllamaChatRequest(
        model = model.value,
        messages = messages.map {
            OllamaChatMessage(
                role = when (it.role) {
                    Role.User -> "user"
                    Role.Assistant -> "assistant"
                    Role.System -> "system"
                    else -> throw IllegalArgumentException("Unknown role: ${it.role}")
                },
                content = when (it) {
                    is UserMessage -> it.content
                    is AssistantMessage -> it.content
                    is SystemMessage -> it.content
                    else -> throw IllegalArgumentException("Unknown message type: $it")
                },
            )
        }
    )
}

@OptIn(ExperimentalSerializationApi::class)
fun CompletionRequest.toOllamaGenerateRequest(): OllamaGenerateRequest {
    return OllamaGenerateRequest(
        model = model.value,
        prompt = prompt,
        stream = stream ?: false,
        raw = (streamOptions?.get("raw") ?: false) as Boolean,
        options = streamOptions?.filter { it.key != "raw" }
    )
}

fun OllamaGenerateResponse.toOpenAICompletion(): Completion {
    return Completion(
        id = createdAt,
        model = model,
        created = 1,
        choices = listOf(
            CompletionChoice(
                text = response,
                index = 0,
                finishReason = doneReason ?: ""
            )
        ),
        usage = Usage(
            promptTokens = promptEvalCount,
            completionTokens = evalCount,
            totalTokens = evalCount?.let { promptEvalCount?.plus(it) }
        )
    )
}