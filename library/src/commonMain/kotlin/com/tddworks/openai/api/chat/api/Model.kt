package com.tddworks.openai.api.chat.api

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class Model(val value: String) {
    companion object {
        val GPT_3_5_TURBO = Model("gpt-3.5-turbo")
        val GPT_3_5_TURBO_0125 = Model("gpt-3.5-turbo-0125")
        val GPT_4 = Model("gpt-4")
        val GPT_4_TURBO_PREVIEW = Model("gpt-4-0125-preview")
        val GPT_4_TURBO = Model("gpt-4-turbo-preview")
        val GPT4_VISION_PREVIEW = Model("gpt-4-vision-preview")
        val DALL_E_2 = Model("dall-e-2")
        val DALL_E_3 = Model("dall-e-3")
    }
}