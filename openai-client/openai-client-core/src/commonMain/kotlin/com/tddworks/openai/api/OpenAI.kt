package com.tddworks.openai.api

import com.tddworks.di.getInstance
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.Completions

interface OpenAI : Chat, Images, Completions {
    companion object {
        const val BASE_URL = "api.openai.com"
    }
}

class OpenAIApi : OpenAI, Chat by getInstance(), Images by getInstance(),
    Completions by getInstance()