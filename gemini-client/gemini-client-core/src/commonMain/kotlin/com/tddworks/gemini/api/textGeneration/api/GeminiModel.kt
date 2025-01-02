package com.tddworks.gemini.api.textGeneration.api

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * https://ai.google.dev/gemini-api/docs/models/gemini
 */
@Serializable
@JvmInline
value class GeminiModel(val value: String) {
    companion object {

        /**
         * Input(s): Audio, images, videos, and text
         * Output(s): Text
         * Optimized for: Complex reasoning tasks requiring more intelligence
         */
        val GEMINI_1_5_PRO = GeminiModel("gemini-1.5-pro")

        /**
         * Input(s): Audio, images, videos, and text
         * Output(s): Text
         * Optimized for: High volume and lower intelligence tasks
         */
        val GEMINI_1_5_FLASH_8b = GeminiModel("gemini-1.5-flash-8b")

        /**
         * Input(s): Audio, images, videos, and text
         * Output(s): Text
         * Optimized for: Fast and versatile performance across a diverse variety of tasks
         */
        val GEMINI_1_5_FLASH = GeminiModel("gemini-1.5-flash")

        /**
         * Input(s): Audio, images, videos, and text
         * Output(s): Text, images (coming soon), and audio (coming soon)
         * Optimized for: Next generation features, speed, and multimodal generation for a diverse variety of tasks
         */
        val GEMINI_2_0_FLASH = GeminiModel("gemini-2.0-flash-exp")

        val availableModels = listOf(
            GEMINI_1_5_PRO,
            GEMINI_1_5_FLASH_8b,
            GEMINI_1_5_FLASH,
            GEMINI_2_0_FLASH
        )
    }
}