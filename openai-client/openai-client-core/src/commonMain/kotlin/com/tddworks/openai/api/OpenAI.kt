package com.tddworks.openai.api

import com.tddworks.di.getInstance
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.images.api.Images

interface OpenAI : Chat, Images {
    companion object {
        const val BASE_URL = "api.openai.com"
    }
}

class OpenAIApi : OpenAI, Chat by getInstance(), Images by getInstance()