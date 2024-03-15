package com.tddworks.anthropic.api.messages.api.stream

import com.tddworks.anthropic.api.messages.api.CreateMessageResponse
import com.tddworks.common.network.api.StreamChatResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("message_start")
@Serializable
data class MessageStart(
    override val type: String,
    val message: CreateMessageResponse,
) : StreamChatResponse

@Serializable
@SerialName("content_block_start")
data class ContentBlockStart(
    override val type: String,
    val index: Int,
    @SerialName("content_block")
    val contentBlock: ContentBlock,
) : StreamChatResponse

@Serializable
@SerialName("content_block_delta")
data class ContentBlock(
    override val type: String,
    val text: String,
): StreamChatResponse

@Serializable
@SerialName("content_block_delta")
data class ContentBlockDelta(
    override val type: String,
    val index: Int,
    val delta: Delta,
): StreamChatResponse

@Serializable
@SerialName("content_block_stop")
data class ContentBlockStop(
    override val type: String,
    val index: Int,
): StreamChatResponse

@Serializable
@SerialName("message_delta")
data class MessageDelta(
    override val type: String,
    val delta: Delta,
): StreamChatResponse

@Serializable
@SerialName("message_stop")
data class MessageStop(
    override val type: String,
): StreamChatResponse

@Serializable
@SerialName("ping")
data class Ping(
    override val type: String,
): StreamChatResponse

@Serializable
data class Delta(
    val type: String,
    val text: String,
)


