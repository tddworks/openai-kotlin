package com.tddworks.openai.api.chat.api

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletion(val id: String, val created: Long, val model: String, val choices: List<ChatChoice>)