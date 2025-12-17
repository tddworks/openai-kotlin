package com.tddworks.openai.gateway.darwin.api

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.ollama.api.Ollama
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.gateway.api.OpenAIGateway

/**
 * Creates an OpenAI Gateway with static configuration.
 *
 * Swift usage:
 * ```swift
 * import OpenAIGateway
 *
 * let gateway = OpenAIGateway(
 *     openAIKey: "your-openai-key",
 *     anthropicKey: "your-anthropic-key",
 *     geminiKey: "your-gemini-key"
 * )
 * ```
 */
fun OpenAIGateway(
    openAIKey: String = "CONFIGURE_ME",
    openAIBaseUrl: String = OpenAI.BASE_URL,
    anthropicKey: String = "CONFIGURE_ME",
    anthropicBaseUrl: String = Anthropic.BASE_URL,
    anthropicVersion: String = Anthropic.ANTHROPIC_VERSION,
    ollamaBaseUrl: String = Ollama.BASE_URL,
    geminiKey: String = "CONFIGURE_ME",
    geminiBaseUrl: String = Gemini.BASE_URL,
): OpenAIGateway = OpenAIGateway.create(
    openAIKey = openAIKey,
    openAIBaseUrl = openAIBaseUrl,
    anthropicKey = anthropicKey,
    anthropicBaseUrl = anthropicBaseUrl,
    anthropicVersion = anthropicVersion,
    ollamaBaseUrl = ollamaBaseUrl,
    geminiKey = geminiKey,
    geminiBaseUrl = geminiBaseUrl,
)

/**
 * Creates an OpenAI Gateway with dynamic configuration.
 * Use this when your API keys or settings may change at runtime.
 *
 * Swift usage:
 * ```swift
 * let gateway = OpenAIGateway(
 *     openAIKey: { Settings.shared.openAIKey },
 *     anthropicKey: { Settings.shared.anthropicKey },
 *     geminiKey: { Settings.shared.geminiKey }
 * )
 * ```
 */
fun OpenAIGateway(
    openAIKey: () -> String = { "CONFIGURE_ME" },
    openAIBaseUrl: () -> String = { OpenAI.BASE_URL },
    anthropicKey: () -> String = { "CONFIGURE_ME" },
    anthropicBaseUrl: () -> String = { Anthropic.BASE_URL },
    anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION },
    ollamaBaseUrl: () -> String = { Ollama.BASE_URL },
    geminiKey: () -> String = { "CONFIGURE_ME" },
    geminiBaseUrl: () -> String = { Gemini.BASE_URL },
): OpenAIGateway = OpenAIGateway.create(
    openAIKey = openAIKey,
    openAIBaseUrl = openAIBaseUrl,
    anthropicKey = anthropicKey,
    anthropicBaseUrl = anthropicBaseUrl,
    anthropicVersion = anthropicVersion,
    ollamaBaseUrl = ollamaBaseUrl,
    geminiKey = geminiKey,
    geminiBaseUrl = geminiBaseUrl,
)
