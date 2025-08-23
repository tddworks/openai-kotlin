package com.tddworks.common.network.api.ktor.internal.exception

/** OpenAI client exception */
sealed class OpenAIException(message: String? = null, throwable: Throwable? = null) :
    RuntimeException(message, throwable)
