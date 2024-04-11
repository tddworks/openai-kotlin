package com.tddworks.openai.api.chat.api

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletion(val id: String, val created: Long, val model: String, val choices: List<ChatChoice>) {
    companion object {
        fun dummy() = ChatCompletion(
            "chatcmpl-8Zu4AF8QMK3zFgdzXIPjFS4VkWErX",
            1634160000,
            "gpt-3.5-turbo",
            listOf(ChatChoice.dummy())
        )
    }
}