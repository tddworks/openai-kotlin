package com.tddworks.anthropic.api

import org.koin.core.component.KoinComponent

/**
 * @param apiKey a function that returns the API key to be used for authentication. Defaults to
 *   "CONFIGURE_ME" if not provided.
 * @param baseUrl a function that returns the base URL of the API. Defaults to the value specified
 *   in the Anthropic companion object if not provided.
 * @param anthropicVersion a function that returns the version of the Anthropic API to be used.
 *   Defaults to "2023-06-01" if not provided.
 */
data class AnthropicConfig(
    val apiKey: () -> String = { "CONFIG_API_KEY" },
    val baseUrl: () -> String = { Anthropic.BASE_URL },
    val anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION },
) : KoinComponent
