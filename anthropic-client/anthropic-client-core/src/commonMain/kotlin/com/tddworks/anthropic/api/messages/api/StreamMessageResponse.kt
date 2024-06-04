package com.tddworks.anthropic.api.messages.api

import com.tddworks.anthropic.api.messages.api.internal.json.StreamMessageResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable(with = StreamMessageResponseSerializer::class)
sealed interface StreamMessageResponse {
    val type: String
}

@Serializable
data class MessageStart(
    override val type: String,
    val message: CreateMessageResponse,
) : StreamMessageResponse

@Serializable
data class ContentBlockStart(
    override val type: String,
    val index: Int,
    @SerialName("content_block")
    val contentBlock: ContentBlock,
) : StreamMessageResponse

@Serializable
data class ContentBlock(
    val type: String,
    val text: String,
)

@Serializable
data class ContentBlockDelta(
    override val type: String,
    val index: Int,
    val delta: Delta,
) : StreamMessageResponse

@Serializable
data class ContentBlockStop(
    override val type: String,
    val index: Int,
) : StreamMessageResponse

@Serializable
data class MessageDelta(
    override val type: String,
    val delta: Delta,
) : StreamMessageResponse

@Serializable
data class MessageStop(
    override val type: String,
) : StreamMessageResponse

@Serializable
data class Ping(
    override val type: String,
) : StreamMessageResponse


