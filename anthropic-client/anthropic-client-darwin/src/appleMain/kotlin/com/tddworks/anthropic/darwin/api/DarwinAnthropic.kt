package com.tddworks.anthropic.darwin.api

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.di.iniAnthropic


/**
 * Object responsible for setting up and initializing the Anthropoc API client.
 */
object DarwinAnthropic {

    /**
     * Initializes the Anthropic library with the provided configuration parameters.
     *
     * @param apiKey a lambda function that returns the API key to be used for authentication
     * @param baseUrl a lambda function that returns the base URL of the Anthropic API
     * @param anthropicVersion a lambda function that returns the version of the Anthropic API to use
     */
    fun anthropic(
        apiKey: () -> String = { "CONFIG_API_KEY" },
        baseUrl: () -> String = { Anthropic.BASE_URL },
        anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION },
    ) = iniAnthropic(AnthropicConfig(apiKey, baseUrl, anthropicVersion))
}
