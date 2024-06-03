package com.tddworks.anthropic.api.messages.api.internal

import kotlinx.serialization.json.Json


/**
 * Represents a JSON object that allows for leniency and ignores unknown keys.
 *
 * @property isLenient Removes JSON specification restriction (RFC-4627) and makes parser more liberal to the malformed input. In lenient mode quoted boolean literals, and unquoted string literals are allowed.
 * Its relaxations can be expanded in the future, so that lenient parser becomes even more permissive to invalid value in the input, replacing them with defaults.
 * false by default.
 * @property ignoreUnknownKeys Specifies whether encounters of unknown properties in the input JSON should be ignored instead of throwing SerializationException. false by default..
 */
val JsonLenient = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
}
