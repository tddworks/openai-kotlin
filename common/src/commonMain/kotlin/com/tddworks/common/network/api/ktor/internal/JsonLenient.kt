package com.tddworks.common.network.api.ktor.internal

import kotlinx.serialization.EncodeDefault
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
    /**
     * When this flag is disabled properties with null values without default are not encoded.
     */
    explicitNulls = false
    /**
     * Controls whether the target property is serialized when its value is equal to a default value,
     * regardless of the format settings.
     * Does not affect decoding and deserialization process.
     *
     * Example of usage:
     * ```
     * @Serializable
     * data class Foo(
     *     @EncodeDefault(ALWAYS) val a: Int = 42,
     *     @EncodeDefault(NEVER) val b: Int = 43,
     *     val c: Int = 44
     * )
     *
     * Json { encodeDefaults = false }.encodeToString((Foo()) // {"a": 42}
     * Json { encodeDefaults = true }.encodeToString((Foo())  // {"a": 42, "c":44}
     * ```
     *
     * @see EncodeDefault.Mode.ALWAYS
     * @see EncodeDefault.Mode.NEVER
     */
    encodeDefaults = true
}