package com.tddworks.openai.api.chat.api

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Representing the available message sender roles.
 * deprecated or obsolete role like `Function` is removed.
 */
@JvmInline
@Serializable
value class Role(val name: String) {
    companion object {
        val System: Role = Role("system")
        val User: Role = Role("user")
        val Assistant: Role = Role("assistant")
        val Tool: Role = Role("tool")
    }
}