@file:Suppress("unused")

package com.tddworks.gemini.darwin.api

import com.tddworks.gemini.api.textGeneration.api.Gemini

/**
 * Re-exports for Swift consumers.
 * All factory methods are available via Gemini.Companion.
 *
 * Usage:
 * ```swift
 * import GeminiClient
 *
 * let client = Gemini.create(apiKey: "your-api-key")
 * ```
 */
typealias GeminiCompanion = Gemini.Companion
