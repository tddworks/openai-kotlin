package com.tddworks.ollama.api

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * https://docs.anthropic.com/claude/docs/models-overview
 * Claude is a family of state-of-the-art large language models developed by Anthropic. Our models are designed to provide you with the best possible experience when interacting with AI, offering a range of capabilities and performance levels to suit your needs and make it easy to deploy high performing, safe, and steerable models. In this guide, we'll introduce you to our latest and greatest models, the Claude 3 family, as well as our legacy models, which are still available for those who need them.
 *
 */
@Serializable
@JvmInline
value class Model(val value: String) {
    companion object {
        /**
         * Most powerful model for highly complex tasks
         * Max output length: 4096 tokens
         * Cost (Input / Output per MTok^) $15.00 / $75.00
         */
        val CLAUDE_3_OPUS = Model("claude-3-opus-20240229")

        /**
         * Ideal balance of intelligence and speed for enterprise workloads
         * Max output length: 4096 tokens
         * Cost (Input / Output per MTok^) $3.00 / $15.00
         */
        val CLAUDE_3_Sonnet = Model("claude-3-sonnet-20240229")

        /**
         * Fastest and most compact model for near-instant responsiveness
         * Max output length: 4096 tokens
         * Cost (Input / Output per MTok^) $0.25 / $1.25
         */
        val CLAUDE_3_HAIKU = Model("claude-3-haiku-20240307")

        val availableModels = listOf(CLAUDE_3_OPUS, CLAUDE_3_Sonnet, CLAUDE_3_HAIKU)
    }
}