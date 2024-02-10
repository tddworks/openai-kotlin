package com.tddworks.openai.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enum representing the available message sender roles.
 */
@Serializable
enum class Role {
    @SerialName("user")
    USER,

    @SerialName("assistant")
    ASSISTANT,

    @SerialName("system")
    SYSTEM,

    @SerialName("tool")
    TOOL
}