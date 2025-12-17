@file:Suppress("unused")

package com.tddworks.ollama.darwin.api

import com.tddworks.ollama.api.Ollama

/**
 * Re-exports for Swift consumers.
 * All factory methods are available via Ollama.Companion.
 *
 * Usage:
 * ```swift
 * import OllamaClient
 *
 * let client = Ollama.create()
 * ```
 */
typealias OllamaCompanion = Ollama.Companion
