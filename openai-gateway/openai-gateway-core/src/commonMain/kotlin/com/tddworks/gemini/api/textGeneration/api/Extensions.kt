package com.tddworks.gemini.api.textGeneration.api

import com.tddworks.openai.api.chat.api.*
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatMessage
import kotlinx.serialization.ExperimentalSerializationApi
import com.tddworks.openai.api.chat.api.ChatCompletionChunk as OpenAIChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletion as OpenAIChatCompletion
import com.tddworks.openai.api.chat.api.ChatMessage.AssistantMessage as OpenAIAssistantMessage
import com.tddworks.openai.api.chat.api.ChatMessage.SystemMessage as OpenAISystemMessage
import com.tddworks.openai.api.chat.api.ChatMessage.UserMessage as OpenAIUserMessage
import com.tddworks.openai.api.chat.api.ChatMessage as OpenAIMessage
import com.tddworks.openai.api.chat.api.Role as OpenAIRole


fun GenerateContentResponse.toOpenAIChatCompletion(): OpenAIChatCompletion {
    val completionId = "chatcmpl-gemini-123"
    val creationTimestamp = 1L

    val chatChoices = candidates.mapIndexed { index, candidate ->
        val textPart = candidate.content.parts.first() as Part.TextPart
        val message = ChatMessage.assistant(textPart.text)
        val reason = candidate.finishReason?.let { FinishReason(it) }

        ChatChoice(
            message = message,
            index = index,
            finishReason = reason
        )
    }

    return ChatCompletion(
        id = completionId,
        created = creationTimestamp,
        model = modelVersion,
        choices = chatChoices
    )
}


fun GenerateContentResponse.toOpenAIChatCompletionChunk(): OpenAIChatCompletionChunk {
    val id = "chatcmpl-gemini-123"
    val created = 1L

    val firstCandidate = candidates.firstOrNull()
    val firstTextPart = firstCandidate?.content?.parts?.firstOrNull() as? Part.TextPart

    val chatChunk = ChatChunk(
        index = 0,
        delta = ChatDelta(
            role = OpenAIRole.Assistant,
            content = firstTextPart?.text
        ),
        finishReason = firstCandidate?.finishReason
    )

    return OpenAIChatCompletionChunk(
        id = id,
        `object` = "gemini-chunk",
        created = created,
        model = modelVersion,
        choices = listOf(chatChunk)
    )
}

@OptIn(ExperimentalSerializationApi::class)
fun ChatCompletionRequest.toGeminiGenerateContentRequest(): GenerateContentRequest {
    val systemMessage = messages.firstOrNull { it.role.name == OpenAIRole.System.name }
        .let { it as? OpenAISystemMessage }?.toGeminiMessage()

    val contentMessages = messages.filter { it.role.name != OpenAIRole.System.name }
        .mapNotNull { it.toGeminiMessageOrNull() }

    return GenerateContentRequest(
        systemInstruction = systemMessage,
        contents = contentMessages,
        model = GeminiModel(model.value),
        stream = stream ?: false
    )
}

private fun OpenAIMessage.toGeminiMessageOrNull(): Content? {
    return when (this) {
        is OpenAIUserMessage -> toGeminiMessage()
        is OpenAIAssistantMessage -> toGeminiMessage()
        else -> throw IllegalArgumentException("Unknown message type: $this")
    }
}


private fun OpenAIUserMessage.toGeminiMessage(): Content {
    return Content(
        parts = listOf(Part.TextPart(text = content)), role = OpenAIRole.User.name
    )
}

private fun OpenAIAssistantMessage.toGeminiMessage(): Content {
    return Content(
        parts = listOf(Part.TextPart(text = content)), role = "model"
    )
}

/**
 * https://ai.google.dev/gemini-api/docs/system-instructions?lang=rest
 * {
 *  "system_instruction": {
 *   "parts": {
 *    "text": "You are Neko the cat respond like one"
 *   }
 *  },
 *  "contents": {
 *   "parts": {
 *    "text": "Good morning! How are you?"
 *   }
 *  }
 * }
 */
private fun OpenAISystemMessage.toGeminiMessage(): Content {
    return Content(
        parts = listOf(Part.TextPart(text = content))
    )
}